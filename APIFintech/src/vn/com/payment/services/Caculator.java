package vn.com.payment.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import vn.com.payment.config.LogType;
import vn.com.payment.entities.TblLoanBill;
import vn.com.payment.entities.TblQuestions;
import vn.com.payment.home.TblQuestionsHome;
import vn.com.payment.object.Fees;
import vn.com.payment.object.ObjMinhhoa;
import vn.com.payment.object.ObjQuestions;
import vn.com.payment.ultities.FileLogger;
import vn.com.payment.ultities.Utils;

public class Caculator {

	public int questionPercent(String userName, List<ObjQuestions> questionsList){
		TblQuestionsHome tblQuestionsHome = new TblQuestionsHome();
		TblQuestions tblQuestions = new TblQuestions();
		int result = 0;
		try {
			int total = 0;
			int ansTrue = 0;
			for (ObjQuestions objQuestions : questionsList) {	
				total = total + 1;
				long questionID = objQuestions.getQuestion_id();
				String ressult = objQuestions.getResult().toJSONString();
				tblQuestions = tblQuestionsHome.getTblQuestions((int)questionID); 
				if(tblQuestions != null){
					if(ressult.contains(tblQuestions.getCorrectAnswears())){
						ansTrue = ansTrue + 1;
					}
				}
			}
			int percent = ansTrue/total*100;
			return percent;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("createrLoan : " + userName + " questionPercent exception" + e, LogType.ERROR);
		}
		return result;
	}
	
	// Tinh minh hoa khoan vay
	public ArrayList<Document> illustrationNew(String userName, String billID, double sotienvay, double sothangvay,
			String ngayvay, double loaitrano, List<Fees> listFee, String loanID) {
		ArrayList<Document> array = new ArrayList<Document>();
		List<TblLoanBill> feesListSet = new ArrayList<>();
		try {
			FileLogger.log("getIllustration: " + userName + " illustrationIns:" + loaitrano, LogType.BUSSINESS);
			double sotienconlai_a = sotienvay;
			double gocconlai = sotienvay;
			double gocconlaiTinhtattoan = sotienvay;
			double laixuatNam = 0;
			double phidichvu = 0;
			double phituvan = 0;
			double phitratruochan = 0;
			double phitattoantruochan = 0;

			double tinhphitranotruochan_a = 0;
			double tienlaithang_a = 0;
			double tinhphidichvu_a = 0;
			double tinhphituvan_a = 0;
			double tientrahangthang_a = 0;
			double tinhphitattoan_a = 0;

			double tinhphitranotruochan = 0;
			double tienlaithang = 0;
			double tinhphidichvu = 0;
			double tinhphituvan = 0;
			double tientrahangthang = 0;
			double tinhphitattoan = 0;
			
			if (loaitrano == 1) {
				// Lịch trả nợ theo dư nợ giảm dần

				int kyvay = 0;
				int tinhPhiTT = 0;
				for (int i = 1; i <= sothangvay; i++) {

					double tiengoctramoiky_a = sotienvay / sothangvay;
					double tiencantt = tienThanhtoan(userName, billID, sotienvay, sothangvay, loaitrano, listFee);
					for (Fees fees : listFee) {
						switch (String.valueOf(fees.getFee_type())) {
						case "1":
							if (fees.getFix_fee_amount() <= 0) {
								laixuatNam = (double) fees.getFix_fee_percent();
								tienlaithang_a = (sotienconlai_a - kyvay * sotienconlai_a / sothangvay) * laixuatNam
										* 30.41666667 / 365;
							} else {
								laixuatNam = (double) fees.getFix_fee_amount();
								tienlaithang_a = laixuatNam;
							}
							break;
						case "2":
							if (fees.getFix_fee_amount() <= 0) {
								phituvan = (double) fees.getFix_fee_percent();
								tinhphituvan_a = (sotienconlai_a - kyvay * sotienconlai_a / sothangvay) * phituvan
										* 30.41666667 / 365;
							} else {
								phituvan = (double) fees.getFix_fee_amount();
								tinhphituvan_a = phituvan;
							}
							break;
						case "3":
							if (fees.getFix_fee_amount() <= 0) {
								phidichvu = (double) fees.getFix_fee_percent();
								tinhphidichvu_a = (sotienconlai_a - kyvay * sotienconlai_a / sothangvay) * phidichvu
										* 30.41666667 / 365;
							} else {
								phidichvu = (double) fees.getFix_fee_amount();
								tinhphidichvu_a = phidichvu;
							}
							break;
						case "4":
							if (fees.getFix_fee_amount() <= 0) {
								phitratruochan = (double) fees.getFix_fee_percent();
								tinhphitranotruochan_a = (sotienconlai_a - kyvay * sotienconlai_a / sothangvay)
										* phitratruochan;
							} else {
								phitratruochan = (double) fees.getFix_fee_amount();
								tinhphitranotruochan_a = phitratruochan;
							}
							break;
						case "5":
							if (fees.getFix_fee_amount() <= 0) {
								phitattoantruochan = (double) fees.getFix_fee_percent();
								tinhphitattoan_a = (sotienconlai_a - kyvay * sotienconlai_a / sothangvay)
										* phitattoantruochan;
								// tinhphitattoan_a = sotienconlai_a +
								// tienlaithang_a + tinhphidichvu_a +
								// tinhphituvan_a + tinhphitranotruochan_a;
								tinhPhiTT = 1;
							} else {
								phitattoantruochan = (double) fees.getFix_fee_amount();
								tinhphitattoan_a = phitattoantruochan;
							}
							break;
						default:
							break;
						}
					}
					// '1:Lai suat, 2:Phi tu van, 3:phi dich vu,4:phitra no
					// trươc han,5:phi tat toan truoc han',
					double tiengoc = tiencantt - (tienlaithang_a + tinhphituvan_a + tinhphidichvu_a);
					if(tinhPhiTT == 1){
						tinhphitattoan_a = gocconlai * phitattoantruochan;
					}
					gocconlai = gocconlai - tiengoc;
					Document doc = new Document("idMinhhoa", billID).append("Kyvay", i)
							.append("Ngaythanhtoan", Utils.getNgayvay(ngayvay))
							.append("Sotiencanthanhtoan", Math.round(tiencantt)).append("Tiengoc", Math.round(tiengoc))
							.append("Tienlai", Math.round(tienlaithang_a))
							.append("Phituvandichvu", Math.round(tinhphituvan_a))
							.append("Phiquanly", Math.round(tinhphidichvu_a))
							.append("Gocconlaisauthanhtoanky", Math.round(gocconlai))
							.append("Phitattoan", Math.round(tinhphitattoan_a))
							// .append("Phitranoquahan",
							// Math.round(tinhphitranotruochan_a))
							.append("Sotientattoantaikynay", Math.round(gocconlaiTinhtattoan + tinhphitattoan_a));
					array.add(doc);
					gocconlaiTinhtattoan = gocconlaiTinhtattoan - tiengoc;
					
					TblLoanBill tblLoanBill = new TblLoanBill();
					tblLoanBill.setLoanId(123);
					tblLoanBill.setLoanRemainAmount((new Double(gocconlai)).longValue());
					tblLoanBill.setCreatedDate(new Date());
					tblLoanBill.setAmtToDecrYourLoan(new BigDecimal(tiengoc));
					tblLoanBill.setMonthlyInterest(new BigDecimal(tienlaithang_a));
					tblLoanBill.setTotalOnAMonth(new BigDecimal(tiencantt));
					tblLoanBill.setAdvisoryFee(new BigDecimal(tinhphituvan_a));
					tblLoanBill.setServiceFee(new BigDecimal(tinhphidichvu_a));
					tblLoanBill.setExtFeeIfIndPayBefore(new BigDecimal(tinhphitattoan_a));
					tblLoanBill.setTotalPayIfSettleRequest(new BigDecimal(gocconlai + tinhphitattoan_a));
					tblLoanBill.setDayMustPay(Integer.parseInt(Utils.getDayMustPay(ngayvay)));
					kyvay = kyvay + 1;
					tblLoanBill.setBillIndex(kyvay);
					feesListSet.add(tblLoanBill);
					
					ngayvay = Utils.getNgayvayNew(Utils.getNgayvay(ngayvay));
					// }
				}
			} else {
				// Lịch trả nợ gốc cuối kỳ
				// double sotientattoantaikynay = 0;
				int kyvay = 0;
				for (int i = 1; i <= sothangvay; i++) {
					double sotienconlai = sotienvay;
					int check = 0;
					ObjMinhhoa objMinhhoa = new ObjMinhhoa();
					for (Fees fees : listFee) {
						switch (String.valueOf(fees.getFee_type())) {
						case "1":
							if (fees.getFix_fee_amount() <= 0) {
								laixuatNam = (double) fees.getFix_fee_percent();
								tienlaithang = sotienconlai * laixuatNam * 30 / 365;
							} else {
								laixuatNam = (double) fees.getFix_fee_amount();
								tienlaithang = laixuatNam;
							}
							break;
						case "2":
							if (fees.getFix_fee_amount() <= 0) {
								phituvan = (double) fees.getFix_fee_percent();
								tinhphituvan = sotienconlai * phituvan * 30 / 365;
							} else {
								phituvan = (double) fees.getFix_fee_amount();
								tinhphituvan = phituvan;
							}
							break;
						case "3":
							if (fees.getFix_fee_amount() <= 0) {
								phidichvu = (double) fees.getFix_fee_percent();
								tinhphidichvu = sotienconlai * phidichvu * 30 / 365;
							} else {
								phidichvu = (double) fees.getFix_fee_amount();
								tinhphidichvu = phidichvu;
							}
							break;
						case "4":
							if (fees.getFix_fee_amount() <= 0) {
								phitratruochan = (double) fees.getFix_fee_percent();
								tinhphitranotruochan = sotienconlai * phitratruochan;
							} else {
								phitratruochan = (double) fees.getFix_fee_amount();
								tinhphitranotruochan = phitratruochan;
							}
							break;
						case "5":
							if (fees.getFix_fee_amount() <= 0) {
								check = 1;
								phitattoantruochan = (double) fees.getFix_fee_percent();
							} else {
								phitattoantruochan = (double) fees.getFix_fee_amount();
								tinhphitattoan = phitattoantruochan;
							}
							break;
						default:
							break;
						}
					}
					if (check == 1) {
						tinhphitattoan = sotienconlai + tienlaithang + tinhphidichvu + tinhphituvan
								+ tinhphitranotruochan;
					}
					tientrahangthang = tienlaithang + tinhphituvan + tinhphidichvu;
					Document doc = new Document("idMinhhoa", billID).append("Kyvay", i)
							.append("Ngaythanhtoan", Utils.getNgayvay(ngayvay))
							.append("Sotiencanthanhtoan", Math.round(tientrahangthang))
							.append("Tiengoc", Math.round(sotienconlai)).append("Tienlai", Math.round(tienlaithang))
							.append("Phituvandichvu", Math.round(tinhphituvan))
							.append("Phiquanly", Math.round(tinhphidichvu))
							.append("Gocconlaisauthanhtoanky", Math.round(sotienconlai))
							.append("Phitattoan", Math.round(tinhphitranotruochan))
							// .append("Phitranoquahan",
							// Math.round(tinhphitranotruochan_a))
							.append("Sotientattoantaikynay", Math.round(tinhphitattoan));
					array.add(doc);

					TblLoanBill tblLoanBill = new TblLoanBill();
					tblLoanBill.setLoanId(123);
					tblLoanBill.setLoanRemainAmount((new Double(gocconlai)).longValue());
					tblLoanBill.setCreatedDate(new Date());
					tblLoanBill.setAmtToDecrYourLoan(new BigDecimal(sotienconlai));
					tblLoanBill.setMonthlyInterest(new BigDecimal(tienlaithang));
					tblLoanBill.setTotalOnAMonth(new BigDecimal(tientrahangthang));
					tblLoanBill.setAdvisoryFee(new BigDecimal(tinhphituvan));
					tblLoanBill.setServiceFee(new BigDecimal(tinhphidichvu));
					tblLoanBill.setExtFeeIfIndPayBefore(new BigDecimal(tinhphitranotruochan));
					tblLoanBill.setTotalPayIfSettleRequest(new BigDecimal(tinhphitattoan));
					tblLoanBill.setDayMustPay(Integer.parseInt(Utils.getDayMustPay(ngayvay)));
					kyvay = kyvay + 1;
					tblLoanBill.setBillIndex(kyvay);
					feesListSet.add(tblLoanBill);

					ngayvay = Utils.getNgayvayNew(Utils.getNgayvay(ngayvay));
					// }
				}
			}

			// Thì tiền tra gốc hàng tháng = số tiền vay / số tháng vay
			// Tiền lãi hàng tháng = số tiền gốc còn lại * lãi năm * số ngày
			// trong tháng / số ngày trong năm (mặc định là 365)
			// Phí dịch vụ sẽ tính bằng = số tiền còn lại * phi dich vu / ngày
			// trong năm * ngày trong tháng
			// phí tất toán trước hạn chỉ bằng = tiền gốc còn lại + lãi trong
			// tháng + phí dịch vụ trong tháng + phi tư vấn trong tháng + phí
			// trả nợ trước hạn (tính theo tháng)
			FileLogger.log("illustration: " + userName + " illustrationIns array insert DB:" + array,
					LogType.BUSSINESS);

			return array;
		} catch (Exception e) {
			FileLogger.log("illustration: " + userName + " illustrationIns exception" + e, LogType.ERROR);
			e.printStackTrace();
		}
		return null;
	}

	// Tinh minh hoa khoan vay
	public List<TblLoanBill> illustrationNewLoanBill(String userName, String billID, double sotienvay,
			double sothangvay, String ngayvay, double loaitrano, List<Fees> listFee, int loanID) {
		ArrayList<Document> array = new ArrayList<Document>();
		List<TblLoanBill> feesListSet = new ArrayList<>();
		try {
			FileLogger.log("getIllustration: " + userName + " illustrationIns:" + loaitrano, LogType.BUSSINESS);
			double sotienconlai_a = sotienvay;
			double gocconlai = sotienvay;
			double gocconlaiTinhtattoan = sotienvay;
			double laixuatNam = 0;
			double phidichvu = 0;
			double phituvan = 0;
			double phitratruochan = 0;
			double phitattoantruochan = 0;

			double tinhphitranotruochan_a = 0;
			double tienlaithang_a = 0;
			double tinhphidichvu_a = 0;
			double tinhphituvan_a = 0;
			double tientrahangthang_a = 0;
			double tinhphitattoan_a = 0;

			double tinhphitranotruochan = 0;
			double tienlaithang = 0;
			double tinhphidichvu = 0;
			double tinhphituvan = 0;
			double tientrahangthang = 0;
			double tinhphitattoan = 0;
			int ngaytrano =  Integer.parseInt(Utils.getNgayTrano((int)sothangvay, ngayvay));
			int tinhPhiTT = 0;
			if (loaitrano == 1) {
				// Lịch trả nợ theo dư nợ giảm dần
				int kyvay = 0;
				for (int i = 1; i <= sothangvay; i++) {
					double tiengoctramoiky_a = sotienvay / sothangvay;
				
					double tiencantt = tienThanhtoan(userName, billID, sotienvay, sothangvay, loaitrano, listFee);
					for (Fees fees : listFee) {
						switch (String.valueOf(fees.getFee_type())) {
						case "1":
							if (fees.getFix_fee_amount() <= 0) {
								laixuatNam = (double) fees.getFix_fee_percent();
								tienlaithang_a = (sotienconlai_a - kyvay * sotienconlai_a / sothangvay) * laixuatNam * 30.41666667 / 365;
							} else {
								laixuatNam = (double) fees.getFix_fee_amount();
								tienlaithang_a = laixuatNam;
							}
							break;
						case "2":
							if (fees.getFix_fee_amount() <= 0) {
								phituvan = (double) fees.getFix_fee_percent();
								tinhphituvan_a = (sotienconlai_a - kyvay * sotienconlai_a / sothangvay) * phituvan
										* 30.41666667 / 365;
							} else {
								phituvan = (double) fees.getFix_fee_amount();
								tinhphituvan_a = phituvan;
							}
							break;
						case "3":
							if (fees.getFix_fee_amount() <= 0) {
								phidichvu = (double) fees.getFix_fee_percent();
								tinhphidichvu_a = (sotienconlai_a - kyvay * sotienconlai_a / sothangvay) * phidichvu * 30.41666667 / 365;
							} else {
								phidichvu = (double) fees.getFix_fee_amount();
								tinhphidichvu_a = phidichvu;
							}
							break;
						case "4":
							if (fees.getFix_fee_amount() <= 0) {
								phitratruochan = (double) fees.getFix_fee_percent();
								tinhphitranotruochan_a = (sotienconlai_a - kyvay * sotienconlai_a / sothangvay)* phitratruochan;
							} else {
								phitratruochan = (double) fees.getFix_fee_amount();
								tinhphitranotruochan_a = phitratruochan;
							}
							break;
						case "5":
							if (fees.getFix_fee_amount() <= 0) {
								phitattoantruochan = (double) fees.getFix_fee_percent();
								tinhphitattoan_a = (sotienconlai_a - kyvay * sotienconlai_a / sothangvay)* phitattoantruochan;
								// tinhphitattoan_a = sotienconlai_a +
								// tienlaithang_a + tinhphidichvu_a +
								// tinhphituvan_a + tinhphitranotruochan_a;
								tinhPhiTT = 1;
							} else {
								phitattoantruochan = (double) fees.getFix_fee_amount();
								tinhphitattoan_a = phitattoantruochan;
							}
							break;
						default:
							break;
						}
					}
					double tiengoc = tiencantt - (tienlaithang_a + tinhphituvan_a + tinhphidichvu_a);
					if(tinhPhiTT == 1){
						tinhphitattoan_a = gocconlai * phitattoantruochan;
					}
					gocconlai = gocconlai - tiengoc;
					Document doc = new Document("idMinhhoa", billID).append("Kyvay", i)
							.append("Ngaythanhtoan", Utils.getNgayvay(ngayvay))
							.append("Sotiencanthanhtoan", Math.round(tiencantt)).append("Tiengoc", Math.round(tiengoc))
							.append("Tienlai", Math.round(tienlaithang_a))
							.append("Phituvandichvu", Math.round(tinhphituvan_a))
							.append("Phiquanly", Math.round(tinhphidichvu_a))
							.append("Gocconlaisauthanhtoanky", Math.round(gocconlai))
							.append("Phitattoan", Math.round(tinhphitattoan_a))
							// .append("Phitranoquahan",
							// Math.round(tinhphitranotruochan_a))
							.append("Sotientattoantaikynay", Math.round(gocconlaiTinhtattoan + tinhphitattoan_a));
							array.add(doc);
							gocconlaiTinhtattoan = gocconlaiTinhtattoan - tiengoc;

					TblLoanBill tblLoanBill = new TblLoanBill();
					tblLoanBill.setLoanId(loanID);
					tblLoanBill.setLoanRemainAmount((new Double(gocconlai)).longValue());
					tblLoanBill.setCreatedDate(new Date());
					tblLoanBill.setAmtToDecrYourLoan(new BigDecimal(tiengoc));
					tblLoanBill.setMonthlyInterest(new BigDecimal(tienlaithang_a));
					tblLoanBill.setTotalOnAMonth(new BigDecimal(tiencantt));
					tblLoanBill.setAdvisoryFee(new BigDecimal(tinhphituvan_a));
					tblLoanBill.setServiceFee(new BigDecimal(tinhphidichvu_a));
					tblLoanBill.setExtFeeIfIndPayBefore(new BigDecimal(tinhphitattoan_a));
					tblLoanBill.setTotalPayIfSettleRequest(new BigDecimal(gocconlai + tinhphitattoan_a));
					tblLoanBill.setDayMustPay(Integer.parseInt(Utils.getDayMustPay(ngayvay)));
					kyvay = kyvay + 1;
					tblLoanBill.setBillIndex(kyvay);
					feesListSet.add(tblLoanBill);
					ngayvay = Utils.getNgayvayNew(Utils.getNgayvay(ngayvay));
					// }
				}
			} else {
				// Lịch trả nợ gốc cuối kỳ
				// double sotientattoantaikynay = 0;
				int kyvay = 0;
				for (int i = 1; i <= sothangvay; i++) {
					double sotienconlai = sotienvay;
					int check = 0;
					ObjMinhhoa objMinhhoa = new ObjMinhhoa();
					for (Fees fees : listFee) {
						switch (String.valueOf(fees.getFee_type())) {
						case "1":
							if (fees.getFix_fee_amount() <= 0) {
								laixuatNam = (double) fees.getFix_fee_percent();
								tienlaithang = sotienconlai * laixuatNam * 30 / 365;
							} else {
								laixuatNam = (double) fees.getFix_fee_amount();
								tienlaithang = laixuatNam;
							}
							break;
						case "2":
							if (fees.getFix_fee_amount() <= 0) {
								phituvan = (double) fees.getFix_fee_percent();
								tinhphituvan = sotienconlai * phituvan * 30 / 365;
							} else {
								phituvan = (double) fees.getFix_fee_amount();
								tinhphituvan = phituvan;
							}
							break;
						case "3":
							if (fees.getFix_fee_amount() <= 0) {
								phidichvu = (double) fees.getFix_fee_percent();
								tinhphidichvu = sotienconlai * phidichvu * 30 / 365;
							} else {
								phidichvu = (double) fees.getFix_fee_amount();
								tinhphidichvu = phidichvu;
							}
							break;
						case "4":
							if (fees.getFix_fee_amount() <= 0) {
								phitratruochan = (double) fees.getFix_fee_percent();
								tinhphitranotruochan = sotienconlai * phitratruochan;
							} else {
								phitratruochan = (double) fees.getFix_fee_amount();
								tinhphitranotruochan = phitratruochan;
							}
							break;
						case "5":
							if (fees.getFix_fee_amount() <= 0) {
								check = 1;
								phitattoantruochan = (double) fees.getFix_fee_percent();
							} else {
								phitattoantruochan = (double) fees.getFix_fee_amount();
								tinhphitattoan = phitattoantruochan;
							}
							break;
						default:
							break;
						}
					}
					if (check == 1) {
						tinhphitattoan = sotienconlai + tienlaithang + tinhphidichvu + tinhphituvan+ tinhphitranotruochan;
					}
					tientrahangthang = tienlaithang + tinhphituvan + tinhphidichvu;

					Document doc = new Document("idMinhhoa", billID).append("Kyvay", i)
							.append("Ngaythanhtoan", Utils.getNgayvay(ngayvay))
							.append("Sotiencanthanhtoan", Math.round(tientrahangthang))
							.append("Tiengoc", Math.round(sotienconlai)).append("Tienlai", Math.round(tienlaithang))
							.append("Phituvandichvu", Math.round(tinhphituvan))
							.append("Phiquanly", Math.round(tinhphidichvu))
							.append("Gocconlaisauthanhtoanky", Math.round(sotienconlai))
							.append("Phitattoan", Math.round(tinhphitranotruochan))
							// .append("Phitranoquahan",
							// Math.round(tinhphitranotruochan_a))
							.append("Sotientattoantaikynay", Math.round(tinhphitattoan));

					array.add(doc);

					TblLoanBill tblLoanBill = new TblLoanBill();
					tblLoanBill.setLoanId(loanID);
					tblLoanBill.setLoanRemainAmount((new Double(gocconlai)).longValue());
					tblLoanBill.setCreatedDate(new Date());
					tblLoanBill.setAmtToDecrYourLoan(new BigDecimal(sotienconlai));
					tblLoanBill.setMonthlyInterest(new BigDecimal(tienlaithang));
					tblLoanBill.setTotalOnAMonth(new BigDecimal(tientrahangthang));
					tblLoanBill.setAdvisoryFee(new BigDecimal(tinhphituvan));
					tblLoanBill.setServiceFee(new BigDecimal(tinhphidichvu));
					tblLoanBill.setExtFeeIfIndPayBefore(new BigDecimal(tinhphitranotruochan));
					tblLoanBill.setTotalPayIfSettleRequest(new BigDecimal(tinhphitattoan));
					tblLoanBill.setDayMustPay(Integer.parseInt(Utils.getDayMustPay(ngayvay)));
					kyvay = kyvay + 1;
					tblLoanBill.setBillIndex(kyvay);
					feesListSet.add(tblLoanBill);

					ngayvay = Utils.getNgayvayNew(Utils.getNgayvay(ngayvay));
					// }
				}
			}

			// Thì tiền tra gốc hàng tháng = số tiền vay / số tháng vay
			// Tiền lãi hàng tháng = số tiền gốc còn lại * lãi năm * số ngày
			// trong tháng / số ngày trong năm (mặc định là 365)
			// Phí dịch vụ sẽ tính bằng = số tiền còn lại * phi dich vu / ngày
			// trong năm * ngày trong tháng
			// phí tất toán trước hạn chỉ bằng = tiền gốc còn lại + lãi trong
			// tháng + phí dịch vụ trong tháng + phi tư vấn trong tháng + phí
			// trả nợ trước hạn (tính theo tháng)
			FileLogger.log("illustration: " + userName + " illustrationIns array insert DB:" + array,
					LogType.BUSSINESS);

			return feesListSet;
		} catch (Exception e) {
			FileLogger.log("illustration: " + userName + " illustrationIns exception" + e, LogType.ERROR);
			e.printStackTrace();
		}
		return null;
	}

	public double tienThanhtoan(String userName, String billID, double sotienvay, double sothangvay, double loaitrano,
			List<Fees> listFee) {
		double laixuatNam = 0;
		double phidichvu = 0;
		double phituvan = 0;
		double sotienconlai_a = sotienvay;
		double tienlaithang_a = 0;
		double tinhphidichvu_a = 0;
		double tinhphituvan_a = 0;

		double sumTiemlai = 0;
		double sumPhituvan = 0; // phituvan = phi tu van
		double sumPhiquanly = 0; // phiquanly = phi dich vu
		double tienTT = 0;
		try {
			int kyvay = 0;
			for (int i = 0; i <= sothangvay; i++) {

				double tiengoctramoiky_a = sotienvay / sothangvay;

				for (Fees fees : listFee) {
					switch (String.valueOf(fees.getFee_type())) {
					case "1":
						if (fees.getFix_fee_amount() <= 0) {
							laixuatNam = (double) fees.getFix_fee_percent();
							tienlaithang_a = (sotienconlai_a - kyvay * sotienconlai_a / sothangvay) * laixuatNam
									* 30.41666667 / 365;
						} else {
							laixuatNam = (double) fees.getFix_fee_amount();
							tienlaithang_a = laixuatNam;
						}
						break;
					case "2":
						if (fees.getFix_fee_amount() <= 0) {
							phituvan = (double) fees.getFix_fee_percent();
							tinhphituvan_a = (sotienconlai_a - kyvay * sotienconlai_a / sothangvay) * phituvan
									* 30.41666667 / 365;
						} else {
							phituvan = (double) fees.getFix_fee_amount();
							tinhphituvan_a = phituvan;
						}
						break;
					case "3":
						if (fees.getFix_fee_amount() <= 0) {
							phidichvu = (double) fees.getFix_fee_percent();
							tinhphidichvu_a = (sotienconlai_a - kyvay * sotienconlai_a / sothangvay) * phidichvu
									* 30.41666667 / 365;
						} else {
							phidichvu = (double) fees.getFix_fee_amount();
							tinhphidichvu_a = phidichvu;
						}
						break;
					default:
						break;
					}
				}

				// '1:Lai suat, 2:Phi tu van, 3:phi dich vu,4:phitra no trươc
				// han,5:phi tat toan truoc han',

				System.out.println("sumTiemlai0: " + sumTiemlai);
				sumTiemlai = sumTiemlai + tienlaithang_a;
				sumPhituvan = sumPhituvan + tinhphituvan_a;// phituvan = phi tu
															// van
				sumPhiquanly = sumPhiquanly + tinhphidichvu_a;// phiquanly = phi
																// dich vu
				kyvay = kyvay + 1;
				System.out.println("----------------");
				System.out.println("kyvay: " + kyvay);
				System.out.println("tienlaithang_a: " + tienlaithang_a);
				System.out.println("sumTiemlai1: " + sumTiemlai);

			}
			System.out.println("sumTiemlai:" + sumTiemlai);
			System.out.println("sumPhituvan:" + sumPhituvan);
			System.out.println("sumPhiquanly:" + sumPhiquanly);
			System.out.println("sothangvay:" + sothangvay);
			tienTT = (sumTiemlai + sumPhituvan + sumPhiquanly + sotienvay) / sothangvay;
			System.out.println("tienTT:" + tienTT);
			return tienTT;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tienTT;
	}

}
