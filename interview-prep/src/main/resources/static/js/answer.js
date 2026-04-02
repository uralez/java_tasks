document.addEventListener('DOMContentLoaded', function () {

    const form          =document.getElementById('answer-form');
    const panelIndex    =document.getElementById('panel-index');
    const rows          =document.querySelectorAll('tr[data-index]');
    let currentAnswer   =answers.length > 0 ? answers[/*[[${currentIndex}]]*/0] : null;

    function fillForm(a) {
        currentAnswer           = a;
        form.action             = '/answer/' + a.id;
        panelIndex.textContent  = ' #' + a.id;
        form.querySelector('#text').value       = a.text;
        form.querySelector('#grade').value      = a.grade;
        form.querySelector('#answerDate').value = a.answerDate;
        form.querySelector('#evaluatorId').value= a.evaluatorId;
        document.getElementById('delete-form').action = '/answer/' + a.id + '/delete';
    }

    rows.forEach(function (row) {
        row.addEventListener('click', function () {
            const idx = parseInt(row.getAttribute('data-index'));
            const a = answers[idx];
            rows.forEach(r => r.classList.remove('answer-row-active'));
            row.classList.add('answer-row-active');
            fillForm(a);
            const currentMode = parseInt(document.querySelector('input[name="mode"]:checked').value);
            updatePanels(idx, currentMode);
        });
    });

    document.getElementById('btn-delete').addEventListener('click', function(){
        const msg = deleteConfirm
            .replace('{0}', currentAnswer.id)
            .replace('{1}', currentAnswer.answerDate);
        if (confirm(msg)){
            document.getElementById('delete-form').submit();
        }
    });

    if (answers.length > 0) {
        fillForm(answers[/*[[${currentIndex}]]*/0]);
    }

    document.querySelectorAll('input[name="mode"]').forEach(function(radio){
        radio.addEventListener('change', function(){
            const mode = parseInt(this.value);
            const panel = document.getElementById('answer-panel');
            panel.setAttribute('data-mode', mode);
            const idx = answers.indexOf(currentAnswer);
            updatePanels(idx, mode);
        });
    });

    document.getElementById('answer-panel').setAttribute('data-mode', 1);
});

function updatePanels(idx, mode) {
    const prev = answers[idx - 1] || null;
    const next = answers[idx + 1] || null;

    const prevPanel = document.getElementById('prev-panel');
    const nextPanel = document.getElementById('next-panel');

    if (mode >= 2 && prev) {
        document.getElementById('prev-index'    ).textContent = '#'+prev.id;
        document.getElementById('prev-text'     ).textContent =     prev.text;
        document.getElementById('prev-grade'    ).textContent =     prev.grade;
        document.getElementById('prev-date'     ).textContent =     prev.answerDate;
        document.getElementById('prev-evaluator').textContent =     prev.evaluatorName;
        prevPanel.style.display = '';
    } else {
        prevPanel.style.display = 'none';
    }

    if (mode >= 3 && next) {
        document.getElementById('next-index'    ).textContent = '#'+next.id;
        document.getElementById('next-text'     ).textContent =     next.text;
        document.getElementById('next-grade'    ).textContent =     next.grade;
        document.getElementById('next-date'     ).textContent =     next.answerDate;
        document.getElementById('next-evaluator').textContent =     next.evaluatorName;
        nextPanel.style.display = '';
    } else {
        nextPanel.style.display = 'none';
    }

}
