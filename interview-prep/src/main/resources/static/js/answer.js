document.addEventListener('DOMContentLoaded', function () {
    const form      = document.getElementById('answer-form');
    const panelIndex= document.getElementById('panel-index');
    const rows      = document.querySelectorAll('tr[data-index]');
    let currentAnswer=answers.length > 0 ? answers[/*[[${currentIndex}]]*/0] : null;
    const cancelled = {};

    // --- Заполняем форму и кнопки-метаданные ---
    function fillForm(a) {
        currentAnswer = a;

        // action form
        form.action = '/answer/' + a.id;
        document.getElementById('delete-form').action = '/answer/' + a.id + '/delete';

        // заголовок панели
        panelIndex.textContent = ' #' + a.id;

        // textarea
        form.querySelector('#text').value = a.text

        // hidden inputs - уйдут на сервер
        document.getElementById('grade'         ).value = a.grade;
        document.getElementById('answerDate'    ).value = a.answerDate;
        document.getElementById('evaluatorId'   ).value = a.evaluatorId;

        // кнопки метаданных - то, что видит пользователь
        document.getElementById('display-grade'     ).textContent = a.grade;
        document.getElementById('display-date'      ).textContent = a.answerDate;
        document.getElementById('display-evaluator' ).textContent = a.evaluatorName;

        // input внутри редакторов - начальные значения для редактирования
        document.getElementById('input-grade'   ).value = a.grade;
        document.getElementById('input-date'    ).value = a.answerDate;

        // выделить текущего оценщика в списке
        document.querySelectorAll('.meta-evaluator-item').forEach(function (item) {
            item.classList.toggle('selected', item.getAttribute('data-id') == a.evaluatorId);
        });

        // textarea - подогнать высоту под текст
        autoResizeTextarea();
    }

    // --- Открыть редактор ---
    function openEditor(name) {
        ['grade', 'date', 'evaluator'].forEach(function (n) {
            if (n !== name) closeEditorSilent(n);
        });
        cancelled[name] = false;
        document.getElementById('editor-' + name).style.display = 'block';
    }

    // закрыть выпадающий диалог без сохранения
    function closeEditorSilent(name) {
        document.getElementById('editor-' + name).style.display = 'none';
    }

    // применить значение и закрыть
    function applyEditor(name) {
        if (name === 'grade'){
            const val = document.getElementById('input-grade').value;
            document.getElementById('display-grade').textContent = val;
            document.getElementById('grade').value = val;
        } else if (name === 'date') {
            const val = document.getElementById('input-date').value;
            document.getElementById('display-date').textContent = val;
            document.getElementById('answerDate').value = val;
        }
        closeEditorSilent(name);
    }

    // --- Крестик - отменить без сохранения ---
    document.getElementById('cancel-grade').addEventListener('click', function (e) {
        e.stopPropagation();
        cancelled['grade'] = true;
        closeEditorSilent('grade');
    });

    document.getElementById('cancel-date').addEventListener('click', function (e) {
        e.stopPropagation();
        cancelled['date'] = true;
        closeEditorSilent('date');
    });

    document.getElementById('cancel-evaluator').addEventListener('click', function (e) {
        e.stopPropagation();
        cancelled['evaluator'] = true;
        closeEditorSilent('evaluator');
    });

    // --- Кнопки метаданных - открыть редактор ---
    document.getElementById('display-grade').addEventListener('click', function () {
        openEditor('grade');
    });

    document.getElementById('display-date').addEventListener('click', function () {
        openEditor('date');
    });

    document.getElementById('display-evaluator').addEventListener('click', function () {
        openEditor('evaluator');
    });

    document.getElementById('text').addEventListener('input', autoResizeTextarea);

    // --- Клик на оценщика в списке ---
    document.querySelectorAll('.meta-evaluator-item').forEach(function (item) {
        item.addEventListener('click', function (e) {
            e.stopPropagation();
            const id    = item.getAttribute('data-id');
            const name  = item.textContent.trim();

            document.getElementById('display-evaluator').textContent = name;
            document.getElementById('evaluatorId').value = id;

            document.querySelectorAll('.meta-evaluator-item').forEach(function (i) {
                i.classList.toggle('selected', i === item);
            });

            cancelled['evaluator'] = false;
            closeEditorSilent('evaluator');
        });
    });

    // --- клик вне редактора - применить (grade и date) или закрыть (evaluator) ---
    document.addEventListener('click', function (e) {
        ['grade', 'date', 'evaluator'].forEach(function (name) {
            const editor= document.getElementById('editor-' + name);
            const btn   = document.getElementById('display-'+ name);

            if (editor.style.display === 'none') return;
            if (editor.contains(e.target) || btn.contains(e.target)) return;

            if (!cancelled[name] && name !== 'evaluator'){
                applyEditor(name);
            } else {
                closeEditorSilent(name);
            }
            cancelled[name] = false;
        });
    });

    // --- клик по строке таблицы ---
    rows.forEach(function (row){
        row.addEventListener('click', function () {
            const idx   = parseInt(row.getAttribute('data-index'));
            const a     = answers[idx];
            rows.forEach(r => r.classList.remove('answer-row-active'));
            row.classList.add('answer-row-active');
            fillForm(a);
            const mode = parseInt(document.querySelector('input[name="mode"]:checked').value);
            updatePanels(idx, mode);
        });
    });

    // --- Delete ---
    document.getElementById('btn-delete').addEventListener('click', function () {
        const msg = deleteConfirm
            .replace('{0}', currentAnswer.id)
            .replace('{1}', currentAnswer.answerDate);
        if (confirm(msg)) {
            document.getElementById('delete-form').submit();
        }
    });

    // --- Mode switcher ---
    document.querySelectorAll('input[name="mode"]').forEach(function (radio) {
        radio.addEventListener('change', function () {
            const mode  = parseInt(this.value);
            document.getElementById('answer-layout').setAttribute('data-mode', mode);
            const idx = answers.indexOf(currentAnswer);
            updatePanels(idx, mode);
        });
    });

    // --- Инициализация ---
    if (answers.length > 0) {
        fillForm(answers[/*[[${currentIndex}]]*/0]);
    }
});

// --- Prev/Next панели (режимы 2 и 3)
function updatePanels(idx, mode) {
    const prev      = answers[idx - 1] || null;
    const next      = answers[idx + 1] || null;

    // ПП
    if (prev) {
        document.getElementById('prev-index'            ).textContent = '#'+prev.id;
        document.getElementById('prev-text'             ).textContent =     prev.text;
        document.getElementById('prev-display-grade'    ).textContent =     prev.grade;
        document.getElementById('prev-display-date'     ).textContent =     prev.answerDate;
        document.getElementById('prev-display-evaluator').textContent =     prev.evaluatorName;
    } else {
        document.getElementById('prev-index'            ).textContent = '';
        document.getElementById('prev-text'             ).textContent = '';
        document.getElementById('prev-display-grade'    ).textContent = '';
        document.getElementById('prev-display-date'     ).textContent = '';
        document.getElementById('prev-display-evaluator').textContent = '';
    }
    // СП
    if (next) {
        document.getElementById('next-index'            ).textContent = '#'+next.id;
        document.getElementById('next-text'             ).textContent =     next.text;
        document.getElementById('next-display-grade'    ).textContent =     next.grade;
        document.getElementById('next-display-date'     ).textContent =     next.answerDate;
        document.getElementById('next-display-evaluator').textContent =     next.evaluatorName;
    } else {
        document.getElementById('next-index'            ).textContent = '';
        document.getElementById('next-text'             ).textContent = '';
        document.getElementById('next-display-grade'    ).textContent = '';
        document.getElementById('next-display-date'     ).textContent = '';
        document.getElementById('next-display-evaluator').textContent = '';
    }
}

// --- авторазмер textarea ---
function autoResizeTextarea() {
    const ta = document.getElementById('text');
    ta.style.height = 'auto';
    ta.style.height = ta.scrollHeight + 'px';
//    ta.style.height = Math.min(ta.scrollHeight, 600) + 'px';
}