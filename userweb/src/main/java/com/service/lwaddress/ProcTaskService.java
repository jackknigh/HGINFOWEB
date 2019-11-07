package com.service.lwaddress;

public interface ProcTaskService {
    /*将短地址中额的|||抹去*/
    public void  Proc_shortAddr();
    /*用于生成新的phone表*/
    public void  Proc_phone();
    /*用于清空被处理完的base_addr表*/
    public void  Proc_destroy();
    /*用于删去表中被处理掉的basics对象，同时将merge_addr中的关联改变*/
    public void  Proc_change();
    /*用于将basics_addr表和insert_addr表联合插入base_addr同时将basic_addr的p3type更新为223,清空Insert_addr表*/
    public void  Proc_unoin();
    /*用于将长号码处理为短号码*/
    public void  Proc_shortPhone();
}
