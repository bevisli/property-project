package app.payment.ali.service.job;

import app.payment.ali.service.pay.PayTransactionService;
import core.framework.inject.Inject;
import core.framework.scheduler.Job;

/**
 * @author mort
 */
public class SyncPaymentsJob implements Job {
    @Inject
    PayTransactionService transactionService;

    @Override
    public void execute() throws Exception {
        transactionService.syncPaymentTransactions("sync-ali-payments-job");
    }
}
