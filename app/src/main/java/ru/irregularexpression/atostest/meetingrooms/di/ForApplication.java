package ru.irregularexpression.atostest.meetingrooms.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * From Jake Wharton's solution: https://github.com/yongjhih/dagger2-sample
 */

@Qualifier @Retention(RetentionPolicy.RUNTIME)
public @interface ForApplication {
}
