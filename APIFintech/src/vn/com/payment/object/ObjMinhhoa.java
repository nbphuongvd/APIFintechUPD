package vn.com.payment.object;

import com.google.gson.Gson;

public class ObjMinhhoa {
	public String kyTrano;
	public long gocConlai;
	public long gocTramoiky;
	public long gocTrakycuoi;
	public long laiThang;
	public long traHangthang;
	public long phiTuvan;
	public long phiDichvu;
	public long phiTranotruochan;
	public long tattoanTruochan;
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
	public String getKyTrano() {
		return kyTrano;
	}
	public void setKyTrano(String kyTrano) {
		this.kyTrano = kyTrano;
	}
	public long getGocConlai() {
		return gocConlai;
	}
	public void setGocConlai(long gocConlai) {
		this.gocConlai = gocConlai;
	}
	public long getGocTramoiky() {
		return gocTramoiky;
	}
	public void setGocTramoiky(long gocTramoiky) {
		this.gocTramoiky = gocTramoiky;
	}
	public long getLaiThang() {
		return laiThang;
	}
	public void setLaiThang(long laiThang) {
		this.laiThang = laiThang;
	}
	public long getTraHangthang() {
		return traHangthang;
	}
	public void setTraHangthang(long traHangthang) {
		this.traHangthang = traHangthang;
	}
	public long getPhiTuvan() {
		return phiTuvan;
	}
	public void setPhiTuvan(long phiTuvan) {
		this.phiTuvan = phiTuvan;
	}
	public long getPhiDichvu() {
		return phiDichvu;
	}
	public void setPhiDichvu(long phiDichvu) {
		this.phiDichvu = phiDichvu;
	}
	public long getPhiTranotruochan() {
		return phiTranotruochan;
	}
	public void setPhiTranotruochan(long phiTranotruochan) {
		this.phiTranotruochan = phiTranotruochan;
	}
	public long getTattoanTruochan() {
		return tattoanTruochan;
	}
	public void setTattoanTruochan(long tattoanTruochan) {
		this.tattoanTruochan = tattoanTruochan;
	}
	public long getGocTrakycuoi() {
		return gocTrakycuoi;
	}
	public void setGocTrakycuoi(long gocTrakycuoi) {
		this.gocTrakycuoi = gocTrakycuoi;
	}
	
}
