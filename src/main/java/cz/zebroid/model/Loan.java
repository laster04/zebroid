package cz.zebroid.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class Loan {
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy - H:mm:ss ");
	
	private Long id;
	private String url;
	private String name;
	private String story;
	private String purpose;
	private List<LoanPhoto> photos = null;
	private String nickName;
	private Integer termInMonths;
	private Double interestRate;
	private String rating;
	private Boolean topped;
	private Integer amount;
	private Integer remainingInvestment;
	private Double investmentRate;
	private Boolean covered;
	private ZonedDateTime datePublished;
	private Boolean published;
	private Date deadline;
	private Integer investmentsCount;
	private Integer questionsCount;
	private String region;
	private String mainIncomeType;
	private Boolean insuranceActive;
	private List<InsuranceHistory> insuranceHistory = null;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getStory() {
		return story;
	}
	
	public void setStory(String story) {
		this.story = story;
	}
	
	public String getPurpose() {
		return purpose;
	}
	
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	public List<LoanPhoto> getPhotos() {
		return photos;
	}
	
	public void setPhotos(List<LoanPhoto> photos) {
		this.photos = photos;
	}
	
	public String getNickName() {
		return nickName;
	}
	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public Integer getTermInMonths() {
		return termInMonths;
	}
	
	public void setTermInMonths(Integer termInMonths) {
		this.termInMonths = termInMonths;
	}
	
	public Double getInterestRate() {
		return interestRate;
	}
	
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
	
	public String getRating() {
		return rating;
	}
	
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	public Boolean getTopped() {
		return topped;
	}
	
	public void setTopped(Boolean topped) {
		this.topped = topped;
	}
	
	public Integer getAmount() {
		return amount;
	}
	
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public Integer getRemainingInvestment() {
		return remainingInvestment;
	}
	
	public void setRemainingInvestment(Integer remainingInvestment) {
		this.remainingInvestment = remainingInvestment;
	}
	
	public Double getInvestmentRate() {
		return investmentRate;
	}
	
	public void setInvestmentRate(Double investmentRate) {
		this.investmentRate = investmentRate;
	}
	
	public Boolean getCovered() {
		return covered;
	}
	
	public void setCovered(Boolean covered) {
		this.covered = covered;
	}
	
	public ZonedDateTime getDatePublished() {
		return datePublished;
	}
	
	public void setDatePublished(ZonedDateTime datePublished) {
		this.datePublished = datePublished;
	}
	
	public Boolean getPublished() {
		return published;
	}
	
	public void setPublished(Boolean published) {
		this.published = published;
	}
	
	public Date getDeadline() {
		return deadline;
	}
	
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	public Integer getInvestmentsCount() {
		return investmentsCount;
	}
	
	public void setInvestmentsCount(Integer investmentsCount) {
		this.investmentsCount = investmentsCount;
	}
	
	public Integer getQuestionsCount() {
		return questionsCount;
	}
	
	public void setQuestionsCount(Integer questionsCount) {
		this.questionsCount = questionsCount;
	}
	
	public String getRegion() {
		return region;
	}
	
	public void setRegion(String region) {
		this.region = region;
	}
	
	public String getMainIncomeType() {
		return mainIncomeType;
	}
	
	public void setMainIncomeType(String mainIncomeType) {
		this.mainIncomeType = mainIncomeType;
	}
	
	public Boolean getInsuranceActive() {
		return insuranceActive;
	}
	
	public void setInsuranceActive(Boolean insuranceActive) {
		this.insuranceActive = insuranceActive;
	}
	
	public List<InsuranceHistory> getInsuranceHistory() {
		return insuranceHistory;
	}
	
	public void setInsuranceHistory(List<InsuranceHistory> insuranceHistory) {
		this.insuranceHistory = insuranceHistory;
	}
	
	@SuppressWarnings("StringBufferReplaceableByString")
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("\n");
		sb.append("-- ").append(getName()).append(" --");
		sb.append("\n");
		sb.append("id: ").append(getId()).append(", datePublish: ").append(getDatePublished().format(formatter))
				.append("\n");
		sb.append("ref: ").append(getUrl()).append(", rating: ").append(getRating());
		return sb.toString();
	}
}
