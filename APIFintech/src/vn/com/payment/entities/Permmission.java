package vn.com.payment.entities;
// Generated 18-May-2021 10:34:34 by Hibernate Tools 3.5.0.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Permmission generated by hbm2java
 */
@Entity
@Table(name = "permmission", catalog = "db_fintech")
public class Permmission implements java.io.Serializable {

	private Integer rowId;
	private String name;
	private int status;
	private String alias;
	private int orderMenu;
	private String icon;

	public Permmission() {
	}

	public Permmission(String name, int status, String alias, int orderMenu, String icon) {
		this.name = name;
		this.status = status;
		this.alias = alias;
		this.orderMenu = orderMenu;
		this.icon = icon;
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

	@Column(name = "name", nullable = false, length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "status", nullable = false)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "alias", nullable = false, length = 500)
	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Column(name = "order_menu", nullable = false)
	public int getOrderMenu() {
		return this.orderMenu;
	}

	public void setOrderMenu(int orderMenu) {
		this.orderMenu = orderMenu;
	}

	@Column(name = "icon", nullable = false, length = 20)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}