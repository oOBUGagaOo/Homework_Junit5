package blow.back;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


@DisplayName("Домашнее задание с параметризованными тестами")
public class JunitWithAnnotationTest {

  @BeforeEach
  void baseConfiguration() {
    Configuration.browser = "chrome";
  }

  @Tag("Medium")
  @DisplayName("Проверка сайта 'Кинопоиск'")
  @ValueSource(strings = {"сериалы", "мультфильмы"})
  @ParameterizedTest(name = "Наличие подсказки на страницы при вводе: {0}")
  void checkingTheHintDisplay(String testData) {
    open("https://www.kinopoisk.ru/");
    $("[name='kp_query']").setValue(testData).pressEnter();
    $(".search_results").shouldHave(text("Скорее всего, вы ищете:"));
  }

  @Tag("Critical")
  @DisplayName("Проверка поиска на сайте")
  @CsvSource(value = {
          "однажды в голливуде, Однажды в… Голливуде",
          "дюна, Дюна"})

  @ParameterizedTest(name = "При нажатии кнопки: {0} отображается: {1}")
  void checkingForButtonsWink(String testData) {
    open("https://www.kinopoisk.ru/");
    $("[name='kp_query']").setValue(testData).pressEnter();
    $(".search_results").shouldHave(text(testData));
  }


  static Stream<Arguments> checkingSearchEngine() {
    return Stream.of(
            Arguments.of("Selenide", "selenide/selenide"),
            Arguments.of("Allure", "allure-framework/allure2")
    );
  }

  @MethodSource
  @Tag("Critical")
  @DisplayName("Проверка строки поиска")
  @ParameterizedTest(name = "В поисковой строке по запросу: {0} отображается результат: {1}")
  void checkingSearchEngine(String testData, String expectedText) {
    Selenide.open("https://github.com/");
    $("input[placeholder='Search GitHub']").setValue(testData).pressEnter();
    $(".repo-list").shouldHave(text(expectedText));
  }

}
