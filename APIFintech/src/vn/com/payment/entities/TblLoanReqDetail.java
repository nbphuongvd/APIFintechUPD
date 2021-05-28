package vn.com.payment.entities;
// Generated 28-May-2021 16:04:13 by Hibernate Tools 3.5.0.Final

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
 * TblLoanReqDetail generated by hbm2java
 */
@Entity
@Table(name = "tbl_loan_req_detail", catalog = "db_fintech")
public class TblLoanReqDetail implements java.io.Serializable {

	private Integer reqDetailId;
	private int loanId;
	private int productId;
	private Integer importFrom;
	private Integer manufactureDate;
	private long expectAmount;
	private String borrowerId;
	private Long approvedAmount;
	private Date createdDate;
	private Date editedDate;
	private Integer disbursementDate;
	private Integer productBrand;
	private String productModal;
	private Integer totalRun;
	private Integer productCondition;
	private Integer productOwnByBorrower;
	private String productSerialNo;
	private String productColor;
	private Integer borrowerType;
	private String borrowerPhone;
	private String borrowerEmail;
	private Integer disburseTo;
	private String disburseToBankNo;
	private String disburseToBankName;
	private String disburseToBankCode;
	private Long productValuation;
	private Long borrowerIncome;
	private String borrowerFullname;
	private String borrowerAddress;
	private String idIssueAt;
	private Integer idIssueDate;
	private String productDesc;
	private Integer borrowerBirthday;
	private String productMachineNumber;
	private String contractSerialNum;
	private String bankBranch;
	private Integer idExpireDate;

	public TblLoanReqDetail() {
	}

	public TblLoanReqDetail(int loanId, int productId, long expectAmount, String borrowerId, Date createdDate) {
		this.loanId = loanId;
		this.productId = productId;
		this.expectAmount = expectAmount;
		this.borrowerId = borrowerId;
		this.createdDate = createdDate;
	}

	public TblLoanReqDetail(int loanId, int productId, Integer importFrom, Integer manufactureDate, long expectAmount,
			String borrowerId, Long approvedAmount, Date createdDate, Date editedDate, Integer disbursementDate,
			Integer productBrand, String productModal, Integer totalRun, Integer productCondition,
			Integer productOwnByBorrower, String productSerialNo, String productColor, Integer borrowerType,
			String borrowerPhone, String borrowerEmail, Integer disburseTo, String disburseToBankNo,
			String disburseToBankName, String disburseToBankCode, Long productValuation, Long borrowerIncome,
			String borrowerFullname, String borrowerAddress, String idIssueAt, Integer idIssueDate, String productDesc,
			Integer borrowerBirthday, String productMachineNumber, String contractSerialNum, String bankBranch,
			Integer idExpireDate) {
		this.loanId = loanId;
		this.productId = productId;
		this.importFrom = importFrom;
		this.manufactureDate = manufactureDate;
		this.expectAmount = expectAmount;
		this.borrowerId = borrowerId;
		this.approvedAmount = approvedAmount;
		this.createdDate = createdDate;
		this.editedDate = editedDate;
		this.disbursementDate = disbursementDate;
		this.productBrand = productBrand;
		this.productModal = productModal;
		this.totalRun = totalRun;
		this.productCondition = productCondition;
		this.productOwnByBorrower = productOwnByBorrower;
		this.productSerialNo = productSerialNo;
		this.productColor = productColor;
		this.borrowerType = borrowerType;
		this.borrowerPhone = borrowerPhone;
		this.borrowerEmail = borrowerEmail;
		this.disburseTo = disburseTo;
		this.disburseToBankNo = disburseToBankNo;
		this.disburseToBankName = disburseToBankName;
		this.disburseToBankCode = disburseToBankCode;
		this.productValuation = productValuation;
		this.borrowerIncome = borrowerIncome;
		this.borrowerFullname = borrowerFullname;
		this.borrowerAddress = borrowerAddress;
		this.idIssueAt = idIssueAt;
		this.idIssueDate = idIssueDate;
		this.productDesc = productDesc;
		this.borrowerBirthday = borrowerBirthday;
		this.productMachineNumber = productMachineNumber;
		this.contractSerialNum = contractSerialNum;
		this.bankBranch = bankBranch;
		this.idExpireDate = idExpireDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "req_detail_id", unique = true, nullable = false)
	public Integer getReqDetailId() {
		return this.reqDetailId;
	}

	public void setReqDetailId(Integer reqDetailId) {
		this.reqDetailId = reqDetailId;
	}

	@Column(name = "loan_id", nullable = false)
	public int getLoanId() {
		return this.loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	@Column(name = "product_id", nullable = false)
	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Column(name = "import_from")
	public Integer getImportFrom() {
		return this.importFrom;
	}

	public void setImportFrom(Integer importFrom) {
		this.importFrom = importFrom;
	}

	@Column(name = "manufacture_date")
	public Integer getManufactureDate() {
		return this.manufactureDate;
	}

	public void setManufactureDate(Integer manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	@Column(name = "expect_amount", nullable = false, precision = 10, scale = 0)
	public long getExpectAmount() {
		return this.expectAmount;
	}

	public void setExpectAmount(long expectAmount) {
		this.expectAmount = expectAmount;
	}

	@Column(name = "borrower_id", nullable = false, length = 20)
	public String getBorrowerId() {
		return this.borrowerId;
	}

	public void setBorrowerId(String borrowerId) {
		this.borrowerId = borrowerId;
	}

	@Column(name = "approved_amount", precision = 10, scale = 0)
	public Long getApprovedAmount() {
		return this.approvedAmount;
	}

	public void setApprovedAmount(Long approvedAmount) {
		this.approvedAmount = approvedAmount;
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

	@Column(name = "disbursement_date")
	public Integer getDisbursementDate() {
		return this.disbursementDate;
	}

	public void setDisbursementDate(Integer disbursementDate) {
		this.disbursementDate = disbursementDate;
	}

	@Column(name = "product_brand")
	public Integer getProductBrand() {
		return this.productBrand;
	}

	public void setProductBrand(Integer productBrand) {
		this.productBrand = productBrand;
	}

	@Column(name = "product_modal")
	public String getProductModal() {
		return this.productModal;
	}

	public void setProductModal(String productModal) {
		this.productModal = productModal;
	}

	@Column(name = "total_run")
	public Integer getTotalRun() {
		return this.totalRun;
	}

	public void setTotalRun(Integer totalRun) {
		this.totalRun = totalRun;
	}

	@Column(name = "product_condition")
	public Integer getProductCondition() {
		return this.productCondition;
	}

	public void setProductCondition(Integer productCondition) {
		this.productCondition = productCondition;
	}

	@Column(name = "product_own_by_borrower")
	public Integer getProductOwnByBorrower() {
		return this.productOwnByBorrower;
	}

	public void setProductOwnByBorrower(Integer productOwnByBorrower) {
		this.productOwnByBorrower = productOwnByBorrower;
	}

	@Column(name = "product_serial_no", length = 50)
	public String getProductSerialNo() {
		return this.productSerialNo;
	}

	public void setProductSerialNo(String productSerialNo) {
		this.productSerialNo = productSerialNo;
	}

	@Column(name = "product_color")
	public String getProductColor() {
		return this.productColor;
	}

	public void setProductColor(String productColor) {
		this.productColor = productColor;
	}

	@Column(name = "borrower_type")
	public Integer getBorrowerType() {
		return this.borrowerType;
	}

	public void setBorrowerType(Integer borrowerType) {
		this.borrowerType = borrowerType;
	}

	@Column(name = "borrower_phone", length = 50)
	public String getBorrowerPhone() {
		return this.borrowerPhone;
	}

	public void setBorrowerPhone(String borrowerPhone) {
		this.borrowerPhone = borrowerPhone;
	}

	@Column(name = "borrower_email")
	public String getBorrowerEmail() {
		return this.borrowerEmail;
	}

	public void setBorrowerEmail(String borrowerEmail) {
		this.borrowerEmail = borrowerEmail;
	}

	@Column(name = "disburse_to")
	public Integer getDisburseTo() {
		return this.disburseTo;
	}

	public void setDisburseTo(Integer disburseTo) {
		this.disburseTo = disburseTo;
	}

	@Column(name = "disburse_to_bank_no", length = 50)
	public String getDisburseToBankNo() {
		return this.disburseToBankNo;
	}

	public void setDisburseToBankNo(String disburseToBankNo) {
		this.disburseToBankNo = disburseToBankNo;
	}

	@Column(name = "disburse_to_bank_name", length = 50)
	public String getDisburseToBankName() {
		return this.disburseToBankName;
	}

	public void setDisburseToBankName(String disburseToBankName) {
		this.disburseToBankName = disburseToBankName;
	}

	@Column(name = "disburse_to_bank_code", length = 50)
	public String getDisburseToBankCode() {
		return this.disburseToBankCode;
	}

	public void setDisburseToBankCode(String disburseToBankCode) {
		this.disburseToBankCode = disburseToBankCode;
	}

	@Column(name = "product_valuation", precision = 10, scale = 0)
	public Long getProductValuation() {
		return this.productValuation;
	}

	public void setProductValuation(Long productValuation) {
		this.productValuation = productValuation;
	}

	@Column(name = "borrower_income", precision = 10, scale = 0)
	public Long getBorrowerIncome() {
		return this.borrowerIncome;
	}

	public void setBorrowerIncome(Long borrowerIncome) {
		this.borrowerIncome = borrowerIncome;
	}

	@Column(name = "borrower_fullname")
	public String getBorrowerFullname() {
		return this.borrowerFullname;
	}

	public void setBorrowerFullname(String borrowerFullname) {
		this.borrowerFullname = borrowerFullname;
	}

	@Column(name = "borrower_address")
	public String getBorrowerAddress() {
		return this.borrowerAddress;
	}

	public void setBorrowerAddress(String borrowerAddress) {
		this.borrowerAddress = borrowerAddress;
	}

	@Column(name = "id_issue_at")
	public String getIdIssueAt() {
		return this.idIssueAt;
	}

	public void setIdIssueAt(String idIssueAt) {
		this.idIssueAt = idIssueAt;
	}

	@Column(name = "id_issue_date")
	public Integer getIdIssueDate() {
		return this.idIssueDate;
	}

	public void setIdIssueDate(Integer idIssueDate) {
		this.idIssueDate = idIssueDate;
	}

	@Column(name = "product_desc")
	public String getProductDesc() {
		return this.productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	@Column(name = "borrower_birthday")
	public Integer getBorrowerBirthday() {
		return this.borrowerBirthday;
	}

	public void setBorrowerBirthday(Integer borrowerBirthday) {
		this.borrowerBirthday = borrowerBirthday;
	}

	@Column(name = "product_machine_number", length = 20)
	public String getProductMachineNumber() {
		return this.productMachineNumber;
	}

	public void setProductMachineNumber(String productMachineNumber) {
		this.productMachineNumber = productMachineNumber;
	}

	@Column(name = "contract_serial_num", length = 30)
	public String getContractSerialNum() {
		return this.contractSerialNum;
	}

	public void setContractSerialNum(String contractSerialNum) {
		this.contractSerialNum = contractSerialNum;
	}

	@Column(name = "bank_branch")
	public String getBankBranch() {
		return this.bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	@Column(name = "id_expire_date")
	public Integer getIdExpireDate() {
		return this.idExpireDate;
	}

	public void setIdExpireDate(Integer idExpireDate) {
		this.idExpireDate = idExpireDate;
	}

}
