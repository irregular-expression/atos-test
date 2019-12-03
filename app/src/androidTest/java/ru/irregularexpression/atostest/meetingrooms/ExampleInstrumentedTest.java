package ru.irregularexpression.atostest.meetingrooms;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.irregularexpression.atostest.meetingrooms.model.DataManager;
import ru.irregularexpression.atostest.meetingrooms.model.web.AuthorizationResponse;
import ru.irregularexpression.atostest.meetingrooms.model.web.ServerResponse;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private Context appContext;
    private DataManager dataManager;

    @Before
    public void setContext() {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();
        dataManager = new DataManager(appContext, null);
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("ru.irregularexpression.atostest.meetingrooms", appContext.getPackageName());
    }

    @Test
    public void test_api() {
        AuthorizationResponse response = dataManager.callApi(dataManager.getApi().getSession("user", "123"), new AuthorizationResponse());
        System.out.println(new Gson().toJson(response));
        assertTrue(response.isSuccess());
    }
}
