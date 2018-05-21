import java.util.ArrayList;

public class Collection
{
  private String collectionName;          // nazwa zbioru
  private int collectionValue;            // wartość zbioru

  public Collection()
  {
    super();
  }

  public Collection(String collectionName, int collectionValue)
  {
    super();
    this.collectionName = collectionName;
    this.collectionValue = collectionValue;
  }

  public String getCollectionName()
  {
    return collectionName;
  }
  public void setCollectionName(String collectionName)
  {
    this.collectionName = collectionName;
  }

  public int getCollectionValue()
  {
    return collectionValue;
  }
  public void setCollectionValue(int collectionValue)
  {
    this.collectionValue = collectionValue;
  }
}
