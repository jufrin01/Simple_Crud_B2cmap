package idb2camp.b2campjufrin.constant;


public enum UserRole {

    ADMIN(1L),
    MANAGER(2L),
    EMPLOYEE(3L),
    BACKER(4L),
    ;

    public long id;

    UserRole(long id) {
        this.id = id;
    }
}
