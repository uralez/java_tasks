// AJAX загрузка вопросов по теме
function loadQuestions(topicId) {
    const questionSelect = document.getElementById('questionId');
    questionSelect.innerHTML = `<option value="">${i18n.selectQuestion}</option>`;
//    questionSelect.innerHTML = '<option value="">- выберите вопрос -</option>';
    if (topicId) {
        fetch('/questions?topicId=' + topicId)
            .then(response => response.json())
            .then(questions => {
                questions.forEach(q => {
                    const option = document.createElement('option');
                    option.value = q.id;
                    option.text = q.text;
                    questionSelect.appendChild(option);
                });
            });
    }
}

// Выбор темы в форме при изменении select
document.getElementById('topicId').addEventListener('change', function () {
    loadQuestions(this.value);
});

// Аккордеон: клик на тему
let gradeChart = null;

document.querySelectorAll('.topic-row').forEach(row => {
    row.style.cursor = 'pointer';
    row.addEventListener('click', function(){
        const topicName = this.getAttribute('data-topic');
        
        // раскрыть/закрыть список вопросов
        const qRow = document.querySelector(`.questions-row[data-topic="${topicName}"]`);
        qRow.style.display = qRow.style.display === 'none' ? 'table-row' : 'none';

        // выбрать тему в форме и очистить вопрос
        const topicSelect = document.getElementById('topicId');
        const matchingOption = Array.from(topicSelect.options).find(o => o.text === topicName);
        if (matchingOption) {
            topicSelect.value = matchingOption.value;
            loadQuestions(matchingOption.value);
        }
    });
});

// клик на вопрос
document.querySelectorAll('.question-item').forEach(item => {
    item.style.cursor = 'pointer';
    item.addEventListener('click', function (e) {
        e.stopPropagation(); // не всплывать к topic-row
        const questionId = this.getAttribute('data-id');
        const topicName = this.getAttribute('data-topic');
        const questionText = this.textContent;

        // выбрать тему в форме
        const topicSelect = document.getElementById('topicId');
        const matchingTopic = Array.from(topicSelect.options).find(o => o.text === topicName);
        if (matchingTopic) {
            topicSelect.value = matchingTopic.value;
            loadQuestions(matchingTopic.value);
        }

        // выбрать вопрос в форме после загрузки вопросов
        setTimeout(() => {
            const questionSelect = document.getElementById('questionId');
            const matchingQuestion = Array.from(questionSelect.options).find(o => o.text === questionText);
            if (matchingQuestion) questionSelect.value = matchingQuestion.value;
        }, 300);

        // загрузить график
        fetch('/grades?questionId=' + questionId)
            .then(response => response.json())
            .then(points => {
                const labels    = points.map(p => p.answerDate);
                const data      = points.map(p => p.grade);

                if (gradeChart) gradeChart.destroy();
                gradeChart = new Chart(document.getElementById('gradeChart'), {
                    type: 'line',
                    data: {
                        labels: labels,
                        datasets: [{
                            label: questionText,
                            data: data,
                            borderColor: '#3d6b4f',
                            backgroundColor: 'rgba(61, 107, 79, 0.1)',
                            pointRadius: 4,
                            tension: 0.3
                        }]
                    },
                    options: {
                        scales: {
                            y: { min: 1, max: 5}
                        }
                    }
                });
            });          
    });
});
