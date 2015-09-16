package jad.patterns.domain.model;
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
		String result = getClass().getSimpleName() + " ";
		if (firstName != null && !firstName.trim().isEmpty())
			result += "firstName: " + firstName;
		return result;
	}
}