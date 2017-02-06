package com.example.magicuhf;

import java.io.Serializable;

/**
 * 读取数据
 * */
public class readmode implements Serializable{
	/**
	 * EPC
	 */
	private String EPCNo="";
	/**
	 * TID
	 */
	private String  TIDNo = "";
	/**
	 * 标签数量
	 * */
	private String  CountNo ="";
	public String getEPCNo() {
		return EPCNo;
	}
	public void setEPCNo(String epcNo) {
		EPCNo = epcNo;
	}
	//
	public String getTIDNo() {
		return TIDNo;
	}
	public void setTIDNo(String tidNo) {
		TIDNo = tidNo;
	}
	public String  getCountNo() {
		return CountNo;
	}
	public void setCountNo(String  countNo) {
		CountNo = countNo;
	}

}
