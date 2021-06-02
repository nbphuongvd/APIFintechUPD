package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

public class ResAllContractList {
	public long status;
	public String message;
	public long totalRecord;
	public List<ResContractList> contract_list;
	public List<ResContractListSponsor> contract_list_sponsor;
	public String toJSON(){
		String json	=	"";
		try {
			Gson gson = new Gson();
			json	=	gson.toJson(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<ResContractList> getContract_list() {
		return contract_list;
	}
	public void setContract_list(List<ResContractList> contract_list) {
		this.contract_list = contract_list;
	}
	public long getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}
	public List<ResContractListSponsor> getContract_list_sponsor() {
		return contract_list_sponsor;
	}
	public void setContract_list_sponsor(List<ResContractListSponsor> contract_list_sponsor) {
		this.contract_list_sponsor = contract_list_sponsor;
	}
	
}
