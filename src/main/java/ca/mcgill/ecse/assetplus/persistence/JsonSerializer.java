package ca.mcgill.ecse.assetplus.persistence;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.common.reflect.ClassPath;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.io.AbstractDriver;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

/**
 * Serializes model elements to/from JSON.
 */
public class JsonSerializer {

  private XStream xStream;

  static final int INDENT_WIDTH = 2;

  /** Constructs a JsonSerializer instance that can serialize domain model classes in the given package. */
  public JsonSerializer(String packageName) {
    this(packageName, new JettisonMappedXmlDriver());
  }

  private JsonSerializer(String packageName, AbstractDriver serializeDriver) {
    xStream = new XStream(serializeDriver);
    xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
    // XStream blocks (de)serialization of all types for security reasons, except allowed types that match this pattern
    xStream.allowTypesByWildcard(new String[] { packageName + ".**" });
    setupXStreamAliases(packageName);
  }

  /** Serializes the given object to the filename. */
  public void serialize(Object object, String filename) {
    try {
      var json = formatJsonString(xStream.toXML(object)) + "\n";
      Files.write(Paths.get(filename), json.getBytes());
    } catch (IOException e) {
      throw new RuntimeException("Could not save data to file '" + filename + "'.");
    }
  }

  /** Deserializes and returns the object stored in the given file. */
  public Object deserialize(String filename) {
    try {
      return xStream.fromXML(new File(filename));
    } catch (XStreamException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Sets up the XStream aliases for model classes to make the output JSON more human-friendly.
   *
   * For example, "ca.mcgill.ecse.btms.model.Route" -> "Route"
   */
  private void setupXStreamAliases(String packageName) {
    var modelPkg = packageName + ".model";
    try {
      ClassPath.from(getClass().getClassLoader()).getAllClasses().stream()
          // filter classes to only include those in the model package
          .filter(clsInfo -> clsInfo.getPackageName().equals(modelPkg))
          // establish 2-way mapping in XStream between model type name <-> its model class
          .forEach(clsInfo -> xStream.alias(clsInfo.getSimpleName(), clsInfo.load()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Formats the input JSON string to make it easier for humans to read. Based on stackoverflow.com/a/46519130.<br>
   *
   * For example:<br>
   * Before: {@code {"licencePlate":"XYZ123","inRepairShop":false}}<br>
   * After:
   * <pre>
     {
       "licencePlate": "XYZ123",
       "inRepairShop": false
     }
   * <pre>
   */
  private static String formatJsonString(final String json) {
    final char[] chars = json.toCharArray();
    final String newline = "\n"; // use this on all platforms

    String result = "";
    boolean beginQuotes = false;

    for (int i = 0, indent = 0; i < chars.length; i++) {
      char c = chars[i];
      if (c == '\"') {
        result += c;
        beginQuotes = !beginQuotes;
        continue; // skip the rest of the for loop body and continue execution at the next iteration
      }
      if (!beginQuotes) {
        result += switch (c) {
          case '{', '[' -> c + newline + String.format("%" + (indent += INDENT_WIDTH) + "s", "");
          case '}', ']' -> newline + ((indent -= INDENT_WIDTH) > 0 ? String.format("%" + indent + "s", "") : "") + c;
          case ':' -> c + " ";
          case ',' -> c + newline + (indent > 0 ? String.format("%" + indent + "s", "") : "");
          default -> "";
        };
        if (Character.isWhitespace(c) || "{}[]:,".contains(Character.toString(c))) {
          continue;
        }
      }
      result += c + (c == '\\' ? "" + chars[++i] : "");
    }

    return result;
  }

}