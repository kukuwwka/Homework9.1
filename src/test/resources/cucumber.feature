@getToken
Feature: Получение токена

  @success
  Scenario: Успешное получение токена
    Given дан URL
    When пользователь отправляет тело запроса
    Then пользователь получает токен
