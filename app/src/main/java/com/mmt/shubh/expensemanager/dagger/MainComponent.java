package com.mmt.shubh.expensemanager.dagger;


import com.mmt.shubh.expensemanager.ExpenseApplication;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MainModule.class, DataModule.class, ApiModule.class})
public interface MainComponent extends DaggerObjectGraph {
    final class Initializer {
        private Initializer() {
        } // No instances.

        public static MainComponent init(ExpenseApplication app) {
            return DaggerMainComponent.builder()
                    .mainModule(new MainModule(app))
                    .build();
        }
    }
}