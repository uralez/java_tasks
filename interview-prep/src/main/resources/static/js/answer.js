document.addEventListener('DOMContentLoaded', function () {

    const form          =document.getElementById('answer-form');
    const panelIndex    =document.getElementById('panel-index');
    const rows          =document.querySelectorAll('tr[data-index]');

    function fillForm(a) {
        form.action             = '/answer/' + a.id;
        panelIndex.textContent  = ' #' + a.id;
        form.querySelector('#text').value       = a.text;
        form.querySelector('#grade').value      = a.grade;
        form.querySelector('#answerDate').value = a.answerDate;
        form.querySelector('#evaluatorId').value= a.evaluatorId;        
    }

    rows.forEach(function (row) {
        row.addEventListener('click', function () {
            const idx = parseInt(row.getAttribute('data-index'));
            const a = answers[idx];
            rows.forEach(r => r.classList.remove('answer-row-active'));
            row.classList.add('answer-row-active');
            fillForm(a);
        });
    });

    if (answers.length > 0) {
        fillForm(answers[/*[[${currentIndex}]]*/0]);
    }
});