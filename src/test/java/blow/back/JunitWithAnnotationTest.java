package blow.back;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


@DisplayName("Домашнее задание с параметризованными тестами")
public class JunitWithAnnotationTest {

    @BeforeEach
    void baseConfiguration() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920 x 1080";
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
}
