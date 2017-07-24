package ua.in.smartjava.domain;

import org.hamcrest.Description;

public class StreetExceptionMatcher implements org.hamcrest.Matcher<Throwable> {
    @Override
    public boolean matches(Object item) {
        return ((Throwable) item).getMessage().equals("Street can not be null");
    }

    @Override
    public void describeMismatch(Object item, Description mismatchDescription) {

    }

    @Override
    public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

    }

    @Override
    public void describeTo(Description description) {

    }
}