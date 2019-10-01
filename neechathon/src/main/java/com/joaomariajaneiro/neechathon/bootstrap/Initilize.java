package com.joaomariajaneiro.neechathon.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Initilize implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ImportData importData;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    importData.importSampleData();
    }
}
