package vn.com.payment.entities;
// Generated 28-May-2021 09:18:48 by Hibernate Tools 3.5.0.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TransasctionRoom generated by hbm2java
 */
@Entity
@Table(name = "transasction_room", catalog = "db_fintech")
public class TransasctionRoom implements java.io.Serializable {

	private Integer rowId;
	private String roomCode;
	private String roomName;
	private int branchId;
	private String address;
	private int status;
	private String manager;

	public TransasctionRoom() {
	}

	public TransasctionRoom(String roomCode, String roomName, int branchId, String address, int status,
			String manager) {
		this.roomCode = roomCode;
		this.roomName = roomName;
		this.branchId = branchId;
		this.address = address;
		this.status = status;
		this.manager = manager;
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

	@Column(name = "room_code", nullable = false, length = 20)
	public String getRoomCode() {
		return this.roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	@Column(name = "room_name", nullable = false, length = 150)
	public String getRoomName() {
		return this.roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	@Column(name = "branch_id", nullable = false)
	public int getBranchId() {
		return this.branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	@Column(name = "address", nullable = false, length = 500)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "status", nullable = false)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "manager", nullable = false, length = 100)
	public String getManager() {
		return this.manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

}
