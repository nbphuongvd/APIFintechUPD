package vn.com.payment.entities;
// Generated May 30, 2021 1:42:24 PM by Hibernate Tools 3.5.0.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TblLoanExpertiseSteps generated by hbm2java
 */
@Entity
@Table(name = "tbl_loan_expertise_steps", catalog = "db_fintech")
public class TblLoanExpertiseSteps implements java.io.Serializable {

	private Integer loanExpertiseId;
	private int loanId;
	private String expertiseUser;
	private Date expertiseDate;
	private Integer expertiseStatus;
	private Integer expertiseStep;
	private String expertiseComment;
	private String loanCode;
	private String action;
	private Date createdDate;

	public TblLoanExpertiseSteps() {
	}

	public TblLoanExpertiseSteps(int loanId) {
		this.loanId = loanId;
	}

	public TblLoanExpertiseSteps(int loanId, String expertiseUser, Date expertiseDate, Integer expertiseStatus,
			Integer expertiseStep, String expertiseComment, String loanCode, String action, Date createdDate) {
		this.loanId = loanId;
		this.expertiseUser = expertiseUser;
		this.expertiseDate = expertiseDate;
		this.expertiseStatus = expertiseStatus;
		this.expertiseStep = expertiseStep;
		this.expertiseComment = expertiseComment;
		this.loanCode = loanCode;
		this.action = action;
		this.createdDate = createdDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "loan_expertise_id", unique = true, nullable = false)
	public Integer getLoanExpertiseId() {
		return this.loanExpertiseId;
	}

	public void setLoanExpertiseId(Integer loanExpertiseId) {
		this.loanExpertiseId = loanExpertiseId;
	}

	@Column(name = "loan_id", nullable = false)
	public int getLoanId() {
		return this.loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	@Column(name = "expertise_user")
	public String getExpertiseUser() {
		return this.expertiseUser;
	}

	public void setExpertiseUser(String expertiseUser) {
		this.expertiseUser = expertiseUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expertise_date", length = 19)
	public Date getExpertiseDate() {
		return this.expertiseDate;
	}

	public void setExpertiseDate(Date expertiseDate) {
		this.expertiseDate = expertiseDate;
	}

	@Column(name = "expertise_status")
	public Integer getExpertiseStatus() {
		return this.expertiseStatus;
	}

	public void setExpertiseStatus(Integer expertiseStatus) {
		this.expertiseStatus = expertiseStatus;
	}

	@Column(name = "expertise_step")
	public Integer getExpertiseStep() {
		return this.expertiseStep;
	}

	public void setExpertiseStep(Integer expertiseStep) {
		this.expertiseStep = expertiseStep;
	}

	@Column(name = "expertise_comment")
	public String getExpertiseComment() {
		return this.expertiseComment;
	}

	public void setExpertiseComment(String expertiseComment) {
		this.expertiseComment = expertiseComment;
	}

	@Column(name = "loan_code")
	public String getLoanCode() {
		return this.loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	@Column(name = "action")
	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "createdDate", length = 10)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
