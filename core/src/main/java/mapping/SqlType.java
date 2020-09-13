package mapping;

public enum SqlType
    {
        SELECT("select"),
        INSERT("insert"),
        UPDATE("update"),
        DELETE("delete"),

        DEFAULT("default");

        private final String value;

        private SqlType(String value)
        {
            this.value = value;
        }

        public String value()
        {
            return this.value;
        }
    }