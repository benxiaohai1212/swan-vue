package com.swancloud.framework.manager;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
public class TransactionCleanup implements DisposableBean {

    @Override
    public void destroy() {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            // 回滚未完成的事务
            TransactionSynchronizationManager.clearSynchronization();
        }
    }
}
