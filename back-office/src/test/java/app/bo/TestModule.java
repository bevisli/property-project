package app.bo;

import core.framework.test.module.AbstractTestModule;

/**
 * @author bevis
 */
public class TestModule extends AbstractTestModule {
    @Override
    protected void initialize() {
        load(new BackOfficeApp());
    }
}
