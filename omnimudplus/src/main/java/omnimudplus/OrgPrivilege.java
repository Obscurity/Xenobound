package omnimudplus;

public enum OrgPrivilege {
	
	// All privileges in one. The administrator is a sole ruler.
	ADMINISTRATOR,
	// Voting on an org referendum or election.
	VOTE,
	// Putting someone into an org.
	INDUCT,
	// Removing someone from an org.
	REMOVE,
	// Orgfile read access.
	READ_ACCESS,
	// Orgfile write access.
	WRITE_ACCESS,
	// Rank editing/naming.
	RANK_EDIT,
	// Communication - send/receive.
	COMMUNICATE;

}
