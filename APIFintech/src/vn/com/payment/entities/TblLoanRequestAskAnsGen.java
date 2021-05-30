package vn.com.payment.entities;

import java.util.List;

public class TblLoanRequestAskAnsGen {
	private Integer QAId;
	private int loanId;
	private String QAThamDinh2;
	private Float thamDinh1Rate;
	private Float thamDinh2Rate;
	public List<Object> QAThamDinh1;
	public Integer getQAId() {
		return QAId;
	}
	public void setQAId(Integer qAId) {
		QAId = qAId;
	}
	public int getLoanId() {
		return loanId;
	}
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	public String getQAThamDinh2() {
		return QAThamDinh2;
	}
	public void setQAThamDinh2(String qAThamDinh2) {
		QAThamDinh2 = qAThamDinh2;
	}
	public Float getThamDinh1Rate() {
		return thamDinh1Rate;
	}
	public void setThamDinh1Rate(Float thamDinh1Rate) {
		this.thamDinh1Rate = thamDinh1Rate;
	}
	public Float getThamDinh2Rate() {
		return thamDinh2Rate;
	}
	public void setThamDinh2Rate(Float thamDinh2Rate) {
		this.thamDinh2Rate = thamDinh2Rate;
	}
	public List<Object> getQAThamDinh1() {
		return QAThamDinh1;
	}
	public void setQAThamDinh1(List<Object> qAThamDinh1) {
		QAThamDinh1 = qAThamDinh1;
	}
	
}
