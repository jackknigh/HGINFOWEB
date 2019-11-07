package com.spinfosec.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperty
{

	/**
	 * 数据库名
	 */
	@Value("${datasource.name}")
	private String dataName;
	/**
	 * 数据库用户名
	 */
	@Value("${datasource.username}")
	private String dataUserName;

	/**
	 * 数据库密码
	 */
	@Value("${datasource.password}")
	private String dataPassWord;

	/**
	 * activeMQ-activemq.broker-url
	 */
	@Value("${activemq.broker-url}")
	private String activemqBrokerUrl;

	/**
	 * activeMQ-activemq.user
	 */
	@Value("${activemq.user}")
	private String activemqUser;

	/**
	 * activeMQ-activemq.password
	 */
	@Value("${activemq.password}")
	private String activemqPassword;

	/**
	 * activeMQ-activemq.ssl.key.store
	 */
//	@Value("${activemq.ssl.key.store}")
//	private String activemqSslKeyStore;
//
//	/**
//	 * activeMQ-activemq.ssl.key.password
//	 */
//	@Value("${activemq.ssl.key.password}")
//	private String activemqSslKeyPassword;
//
//	/**
//	 * activeMQ-activemq.ssl.trust.store
//	 */
//	@Value("${activemq.ssl.trust.store}")
//	private String activemqSslTrustStore;
//
//	/**
//	 * activeMQ-activemq.ssl.trust.password
//	 */
//	@Value("${activemq.ssl.trust.password}")
//	private String activemqSslTrustPassword;

    /**
     * 供SE调用的thrift解析服务的端口
     */
    @Value("${thrift.se.invoke.port}")
    private String thriftSeInvokePort;

    /**
     * 调用文件解析服务的ip
     */
    @Value("${thrift.fileParse.ip}")
    private String thriftFileParseIp;

    /**
     * 调用文件解析服务的端口
     */
    @Value("${thrift.fileParse.port}")
    private String thriftFileParsePort;

    /**
     * 调用文件解析服务的超时时间
     */
    @Value("${thrift.fileParse.timeout}")
    private String thriftFileParseTimeout;

    /**
     * 加密文件分析器执行中线程池中线程等待时间，单位minute
     */
    @Value("${encrypt.analyze.thread.timeout}")
    private String encryptAnalyzeThreadTimeout;

    /*
    * solr根路径
    * */
    @Value("${solr.base.url}")
	private String solrBaseUrl;

	/*
	 * solr案件core路径，用于拼接根路径
	 * */
    @Value("${solr.core.aj}")
	private String solrCoreAj;

	/*
	 * solr接处警core路径，用于拼接根路径
	 * */
    @Value("${solr.core.jcj}")
	private String solrCoreJcj;

	/*
	 * solr重点人员core路径，用于拼接根路径
	 * */
    @Value("${solr.core.zdry}")
	private String solrCoreZdry;

	/*
	 * solr常住人口core路径，用于拼接根路径
	 * */
    @Value("${solr.core.czrk}")
	private String solrCoreCzrk;

	/*
	 * solr暂住人口core路径，用于拼接根路径
	 * */
    @Value("${solr.core.zzrk}")
	private String solrCoreZzrk;

	public String getSolrBaseUrl() {
		return solrBaseUrl;
	}

	public void setSolrBaseUrl(String solrBaseUrl) {
		this.solrBaseUrl = solrBaseUrl;
	}

	public String getSolrCoreAj() {
		return solrCoreAj;
	}

	public void setSolrCoreAj(String solrCoreAj) {
		this.solrCoreAj = solrCoreAj;
	}

	public String getSolrCoreJcj() {
		return solrCoreJcj;
	}

	public void setSolrCoreJcj(String solrCoreJcj) {
		this.solrCoreJcj = solrCoreJcj;
	}

	public String getSolrCoreZdry() {
		return solrCoreZdry;
	}

	public void setSolrCoreZdry(String solrCoreZdry) {
		this.solrCoreZdry = solrCoreZdry;
	}

	public String getSolrCoreCzrk() {
		return solrCoreCzrk;
	}

	public void setSolrCoreCzrk(String solrCoreCzrk) {
		this.solrCoreCzrk = solrCoreCzrk;
	}

	public String getSolrCoreZzrk() {
		return solrCoreZzrk;
	}

	public void setSolrCoreZzrk(String solrCoreZzrk) {
		this.solrCoreZzrk = solrCoreZzrk;
	}

	public String getDataUserName()
	{
		return dataUserName;
	}

	public void setDataUserName(String dataUserName)
	{
		this.dataUserName = dataUserName;
	}

	public String getDataPassWord()
	{
		return dataPassWord;
	}

	public void setDataPassWord(String dataPassWord)
	{
		this.dataPassWord = dataPassWord;
	}

	public String getDataName()
	{
		return dataName;
	}

	public void setDataName(String dataName)
	{
		this.dataName = dataName;
	}

	public String getActivemqBrokerUrl()
	{
		return activemqBrokerUrl;
	}

	public void setActivemqBrokerUrl(String activemqBrokerUrl)
	{
		this.activemqBrokerUrl = activemqBrokerUrl;
	}

	public String getActivemqUser()
	{
		return activemqUser;
	}

	public void setActivemqUser(String activemqUser)
	{
		this.activemqUser = activemqUser;
	}

	public String getActivemqPassword()
	{
		return activemqPassword;
	}

	public void setActivemqPassword(String activemqPassword)
	{
		this.activemqPassword = activemqPassword;
	}

//	public String getActivemqSslKeyStore()
//	{
//		return activemqSslKeyStore;
//	}
//
//	public void setActivemqSslKeyStore(String activemqSslKeyStore)
//	{
//		this.activemqSslKeyStore = activemqSslKeyStore;
//	}
//
//	public String getActivemqSslKeyPassword()
//	{
//		return activemqSslKeyPassword;
//	}
//
//	public void setActivemqSslKeyPassword(String activemqSslKeyPassword)
//	{
//		this.activemqSslKeyPassword = activemqSslKeyPassword;
//	}
//
//	public String getActivemqSslTrustPassword()
//	{
//		return activemqSslTrustPassword;
//	}
//
//	public void setActivemqSslTrustPassword(String activemqSslTrustPassword)
//	{
//		this.activemqSslTrustPassword = activemqSslTrustPassword;
//	}
//
//	public String getActivemqSslTrustStore()
//	{
//		return activemqSslTrustStore;
//	}
//
//	public void setActivemqSslTrustStore(String activemqSslTrustStore)
//	{
//		this.activemqSslTrustStore = activemqSslTrustStore;
//	}

    public String getThriftSeInvokePort()
    {
        return thriftSeInvokePort;
    }

    public void setThriftSeInvokePort(String thriftSeInvokePort)
    {
        this.thriftSeInvokePort = thriftSeInvokePort;
    }

    public String getThriftFileParseIp()
    {
        return thriftFileParseIp;
    }

    public void setThriftFileParseIp(String thriftFileParseIp)
    {
        this.thriftFileParseIp = thriftFileParseIp;
    }

    public String getThriftFileParsePort()
    {
        return thriftFileParsePort;
    }

    public void setThriftFileParsePort(String thriftFileParsePort)
    {
        this.thriftFileParsePort = thriftFileParsePort;
    }

    public String getThriftFileParseTimeout()
    {
        return thriftFileParseTimeout;
    }

    public void setThriftFileParseTimeout(String thriftFileParseTimeout)
    {
        this.thriftFileParseTimeout = thriftFileParseTimeout;
    }

    public String getEncryptAnalyzeThreadTimeout()
    {
        return encryptAnalyzeThreadTimeout;
    }

    public void setEncryptAnalyzeThreadTimeout(String encryptAnalyzeThreadTimeout)
    {
        this.encryptAnalyzeThreadTimeout = encryptAnalyzeThreadTimeout;
    }
}
