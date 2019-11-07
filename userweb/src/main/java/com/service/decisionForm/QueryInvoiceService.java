package com.service.decisionForm;

import java.util.List;

public interface QueryInvoiceService {
    public List queryInvoice(String beginTime, String endTime, String type);
}
