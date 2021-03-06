package vn.com.payment.entities;
// Generated May 19, 2021 11:20:55 PM by Hibernate Tools 3.5.0.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TblBanks generated by hbm2java
 */
@Entity
@Table(name = "tbl_banks", catalog = "db_fintech")
public class TblBanks implements java.io.Serializable {

	private Integer rowId;
	private String bankName;
	private String bankCode;
	private int status;
	private String banhSortName;
	private Integer bankSupportFunction;

	public TblBanks() {
	}

	public TblBanks(String bankName, String bankCode, int status, String banhSortName) {
		this.bankName = bankName;
		this.bankCode = bankCode;
		this.status = status;
		this.banhSortName = banhSortName;
	}

	public TblBanks(String bankName, String bankCode, int status, String banhSortName, Integer bankSupportFunction) {
		this.bankName = bankName;
		this.bankCode = bankCode;
		this.status = status;
		this.banhSortName = banhSortName;
		this.bankSupportFunction = bankSupportFunction;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "row_id", unique = true, nullable = false)
	public Integer getRowId() {
		return this.rowId;
	}

	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}

	@Column(name = "bank_name", nullable = false, length = 200)
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "bank_code", nullable = false, length = 50)
	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "status", nullable = false)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "banh_sort_name", nullable = false, length = 100)
	public String getBanhSortName() {
		return this.banhSortName;
	}

	public void setBanhSortName(String banhSortName) {
		this.banhSortName = banhSortName;
	}

	@Column(name = "bank_support_function")
	public Integer getBankSupportFunction() {
		return this.bankSupportFunction;
	}

	public void setBankSupportFunction(Integer bankSupportFunction) {
		this.bankSupportFunction = bankSupportFunction;
	}

}
