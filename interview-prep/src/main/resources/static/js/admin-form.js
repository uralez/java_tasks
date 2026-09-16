document.addEventListener('DOMContentLoaded', () => {
    const textareas = document.querySelectorAll('.lang-textarea');
    const btnSave = document.getElementById('btn-save');
    const btnTranslate = document.getElementById('btn-translate');

    function updateButtons() {
        let filledCount = 0;
        textareas.forEach(t => {
            if (t.value.trim() !== '') {
                filledCount++;
            }
        });

        btnSave.disabled = filledCount < textareas.length;
        btnTranslate.disabled = filledCount === 0;
    }

    textareas.forEach(t => {
        t.addEventListener('input', updateButtons);
    });

    updateButtons();
});