package System.SystemInterface;

import java.lang.reflect.Field;

public abstract class AbstractDTO {

    AbstractDTO() { }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append( this.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = this.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  ) {
            result.append("  ");
            try {
                result.append( field.getName() );
                result.append(": ");
                //requires access to private field:
                result.append( field.get(this) );
            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }

    public static String varNameToPresentableString(String varName){
        StringBuilder sb = new StringBuilder();
        sb.append(Character.toUpperCase(varName.charAt(0)));
        for(int i=1; i<varName.length(); i++) {
            char c = varName.charAt(i);
            if(Character.isUpperCase(c)){
                sb.append(' ');
                sb.append(Character.toLowerCase(c));
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
