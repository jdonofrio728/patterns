package jad.patterns.data.model;
public abstract class Person extends DomainModel{

	private String firstName;
    private String middleName;
    private String lastName;

	public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
    public void setMiddleName(String middleName) {this.middleName = middleName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

	@Override
	public String toString() {
		return "Person{" +
				"Id=" + getId() + '\'' +
				"firstName='" + firstName + '\'' +
				", middleName='" + middleName + '\'' +
				", lastName='" + lastName + '\'' +
				'}';
	}
}