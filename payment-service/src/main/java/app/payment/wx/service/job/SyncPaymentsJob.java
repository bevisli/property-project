package app.payment.wx.service.job;

import app.payment.wx.service.pay.PayTransactionService;
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
        transactionService.syncPaymentTransactions("sync-wx-payments-job");
    }
}
