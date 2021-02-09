package ru.mts.test.example.gorest.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.UUID;

public class AllureLogger {

    private static final Logger log = LoggerFactory.getLogger(AllureLogger.class);

    public AllureLogger() {
    }

    public void attachText(String text) {
        Allure.addAttachment("Вложение", "text/plain", text);
    }

    public <T> Matcher<T> logMatcher(Matcher<T> matcher) {
        return new AllureLogger.HamcrestLogger(matcher);
    }

    public <T> Matcher<T> logMatcherWithText(String text, Matcher<T> matcher) {
        return new AllureLogger.HamcrestLogger<>(text, matcher);
    }

    private class HamcrestLogger<T> extends BaseMatcher<T> {
        private Matcher<T> matcher;
        private String description;

        public HamcrestLogger(Matcher<T> matcher) {
            this.matcher = matcher;
        }

        public HamcrestLogger(String description, Matcher<T> matcher) {
            this.description = description;
            this.matcher = matcher;
        }

        public boolean matches(Object actual) {
            boolean isMatched = this.matcher.matches(actual);
            String uuid = UUID.randomUUID().toString();
            String message = MessageFormat.format("Checking \"{0}\" and {1}", actual.toString(), this.matcher.toString());
            StepResult result = new StepResult();

            if (this.description == null) {
                result.setName("Проверка");
            } else {
                result.setName(this.description);
            }

            Allure.getLifecycle().startStep(uuid, result);

            try {

                if (isMatched) {
                    AllureLogger.this.attachText(message);
                    Allure.getLifecycle().updateStep(uuid, (s) -> {
                        s.setStatus(Status.PASSED);
                    });
                } else {
                    AllureLogger.this.attachText(message);
                    Allure.getLifecycle().updateStep(uuid, (s) -> {
                        s.setStatus(Status.FAILED);
                    });
                }
            } finally {
                Allure.getLifecycle().stopStep(uuid);
            }
            return isMatched;
        }

        public void describeTo(Description description) {
            this.matcher.describeTo(description);
        }
    }
}