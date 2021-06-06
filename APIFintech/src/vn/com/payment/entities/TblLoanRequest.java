package vn.com.payment.entities;
// Generated 28-May-2021 16:02:37 by Hibernate Tools 3.5.0.Final

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
 * TblLoanRequest generated by hbm2java
 */
@Entity
@Table(name = "tbl_loan_request", catalog = "db_fintech")
public class TblLoanRequest implements java.io.Serializable {

	private Integer loanId;
	private Date createdDate;
	private Date editedDate;
	private Date expireDate;
	private Date approvedDate;
	private String createdBy;
	private String approvedBy;
	private Integer finalStatus;
	private Integer previousStatus;
	private Integer sponsorId;
	private Date latestUpdate;
	private String approveRejectedReason;
	private Integer calculateProfitType;
	private Integer loanForMonth;
	private String loanCode;
	private String loanName;
	private String contractSerialNum;
	private Integer branchId;
	private Integer roomId;
	private Integer extendStatus;

	public TblLoanRequest() {
	}

	public TblLoanRequest(Date createdDate, String createdBy, String loanCode) {
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.loanCode = loanCode;
	}

	public TblLoanRequest(Date createdDate, Date editedDate, Date expireDate, Date approvedDate, String createdBy,
			String approvedBy, Integer finalStatus, Integer previousStatus, Integer sponsorId, Date latestUpdate,
			String approveRejectedReason, Integer calculateProfitType, Integer loanForMonth, String loanCode,
			String loanName, String contractSerialNum, Integer branchId, Integer roomId, Integer extendStatus) {
		this.createdDate = createdDate;
		this.editedDate = editedDate;
		this.expireDate = expireDate;
		this.approvedDate = approvedDate;
		this.createdBy = createdBy;
		this.approvedBy = approvedBy;
		this.finalStatus = finalStatus;
		this.previousStatus = previousStatus;
		this.sponsorId = sponsorId;
		this.latestUpdate = latestUpdate;
		this.approveRejectedReason = approveRejectedReason;
		this.calculateProfitType = calculateProfitType;
		this.loanForMonth = loanForMonth;
		this.loanCode = loanCode;
		this.loanName = loanName;
		this.contractSerialNum = contractSerialNum;
		this.branchId = branchId;
		this.roomId = roomId;
		this.extendStatus = extendStatus;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "loan_id", unique = true, nullable = false)
	public Integer getLoanId() {
		return this.loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "edited_date", length = 19)
	public Date getEditedDate() {
		return this.editedDate;
	}

	public void setEditedDate(Date editedDate) {
		this.editedDate = editedDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expire_date", length = 19)
	public Date getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approved_date", length = 19)
	public Date getApprovedDate() {
		return this.approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	@Column(name = "created_by", nullable = false, length = 100)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "approved_by", length = 100)
	public String getApprovedBy() {
		return this.approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	@Column(name = "final_status")
	public Integer getFinalStatus() {
		return this.finalStatus;
	}

	public void setFinalStatus(Integer finalStatus) {
		this.finalStatus = finalStatus;
	}

	@Column(name = "previous_status")
	public Integer getPreviousStatus() {
		return this.previousStatus;
	}

	public void setPreviousStatus(Integer previousStatus) {
		this.previousStatus = previousStatus;
	}

	@Column(name = "sponsor_id")
	public Integer getSponsorId() {
		return this.sponsorId;
	}

	public void setSponsorId(Integer sponsorId) {
		this.sponsorId = sponsorId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "latest_update", length = 19)
	public Date getLatestUpdate() {
		return this.latestUpdate;
	}

	public void setLatestUpdate(Date latestUpdate) {
		this.latestUpdate = latestUpdate;
	}

	@Column(name = "approve_rejected_reason")
	public String getApproveRejectedReason() {
		return this.approveRejectedReason;
	}

	public void setApproveRejectedReason(String approveRejectedReason) {
		this.approveRejectedReason = approveRejectedReason;
	}

	@Column(name = "calculate_profit_type")
	public Integer getCalculateProfitType() {
		return this.calculateProfitType;
	}

	public void setCalculateProfitType(Integer calculateProfitType) {
		this.calculateProfitType = calculateProfitType;
	}

	@Column(name = "loan_for_month")
	public Integer getLoanForMonth() {
		return this.loanForMonth;
	}

	public void setLoanForMonth(Integer loanForMonth) {
		this.loanForMonth = loanForMonth;
	}

	@Column(name = "loan_code", nullable = false)
	public String getLoanCode() {
		return this.loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	@Column(name = "loan_name")
	public String getLoanName() {
		return this.loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	@Column(name = "contract_serial_num", length = 30)
	public String getContractSerialNum() {
		return this.contractSerialNum;
	}

	public void setContractSerialNum(String contractSerialNum) {
		this.contractSerialNum = contractSerialNum;
	}

	@Column(name = "branch_id")
	public Integer getBranchId() {
		return this.branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	@Column(name = "room_id")
	public Integer getRoomId() {
		return this.roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
	@Column(name = "extend_status")
	public Integer getExtendStatus() {
		return this.extendStatus;
	}

	public void setExtendStatus(Integer extendStatus) {
		this.extendStatus = extendStatus;
	}
}
