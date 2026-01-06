package com.vss.quartz.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@Configuration
public class TransactionManagerConfig {

    @Bean
    public UserTransaction userTransaction() throws SystemException {
        UserTransactionImp utx = new UserTransactionImp();
        utx.setTransactionTimeout(300); // seconds
        return utx;
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public UserTransactionManager atomikosTransactionManager() {
        UserTransactionManager utm = new UserTransactionManager();
        utm.setForceShutdown(false);
        return utm;
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            UserTransaction userTransaction,
            UserTransactionManager atomikosTransactionManager
    ) {
        return new JtaTransactionManager(
                userTransaction,
                atomikosTransactionManager
        );
    }
}
