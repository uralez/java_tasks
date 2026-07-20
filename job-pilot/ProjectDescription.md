# Конспект беседы — два проекта

## Проект 1: interview-prep

**Стек:** Java 23, Spring Boot 3.5.11, Gradle, PostgreSQL 17.5, Liquibase, Hibernate/JPA, Spring Security 6, Thymeleaf, Lombok  
**Пакет:** `com.ayakovlev.interviewprep`  
**CSS переменные:** `--bg`, `--surface`, `--border`, `--accent: #3d6b4f`, `--accent2`, `--text`, `--muted`, `--error`, `--radius: 14px`

### Закрытые issue
- **#20** — страница просмотра/редактирования ответов (`answer.html`, `AnswerController`, `answer.js`)
- **#21** — рефакторинг: фрагменты `fragments/head.html` и `fragments/topbar.html`
- **#22** — badge `#id` у тем и вопросов на `index.html`. Добавлено поле `topicId` в цепочку: `AnswerRepository` → `TopicQuestionRow` → `TopicWithQuestionsDto` → `index.html`. Исправлен JS: `this.querySelector('span:last-child').textContent.trim()`
- **#3** — Redesign answer panel: textarea на всю панель с автоподгонкой высоты; grade/date/evaluator в кнопки-чипы с inline-редакторами (клик вне → применяет, ✕ → отменяет); три режима раскладки через CSS `grid-template-areas` + `data-mode`; ПП и СП — только просмотр
- **#4** — кастомный confirm-dialog вместо браузерного `confirm()`. Разметка в `fragments/confirm-dialog.html`, стили в `main.css`. `topicName` и `questionText` передаются из контроллера с учётом локали

### В процессе — #5: Логирование
В `AnswerService` нужно:
- заменить конкатенацию `+` на плейсхолдеры `{}` в `deleteById`
- добавить `log.info` в `save` (id, student, question)
- добавить `log.warn` в `findById` когда ответ не найден

В `AnswerController` нужно добавить:
- `log.info` при просмотре, сохранении, удалении
- `log.warn` при попытке доступа к чужому ответу (403)

### Ещё не сделано
- **#6** — Управление студентами для ADMIN
- **#7** — i18n для контента вопросов

---

## Проект 2: job-pilot (новый, только создан)

**Стек:** тот же что у interview-prep, Java 21  -> поменяли на 23
**Пакет:** `com.ayakovlev.job-pilot`  
**Group:** `com.ayakovlev`, **Artifact:** `job-pilot`

**Идея проекта:** автоматизация поиска работы — поиск вакансий LinkedIn по критериям пользователя, AI-оценка соответствия резюме, генерация cover letter, хранение истории заявок.

**Архитектура:** многопользовательская — каждый пользователь имеет свой аккаунт и свои настройки поиска.

**Сущности первого спринта:**
- `User` — login, password, name, email, role
- `SearchProfile` — searchQuery (например: middle java developer Germany visa support) - строка поиска в Линкедин
- критерии отбора и оценка уровня соответствия с помощью ИИ моего резюме с описанием каждой вакансии с дополнительными требованиями (например: * не нужен высокий уровень немецкого (не выше B1); * предпочтительно, чтобы было visa support или visa sponsorship; * соответствие вакансии моему опыту было не менее 70% (варьируемый параметр))
Для найденных вакансий надо отправлять аппликации  (5 штук в день - настраиваемо) со сгенерированной cover letter, а у себя локально сохранять: (* фирма
* ссылка на вакансию
* дата аппликации
* cover letter
* параметры формы, указанные при заполнении заявки
* что-то ещё)

**Статус:** проект создан через start.spring.io, открыт в IDEA, ошибка с версией Java исправлена (было 21, поменял на 23 в `build.gradle.kts`).

**Следующий шаг:** настроить `build.gradle.kts` (Spring Boot 3.5.3 вместо 4.1.0), добавить зависимости, создать БД, настроить `application.properties`, сделать страницы login/register по образцу первого проекта.
