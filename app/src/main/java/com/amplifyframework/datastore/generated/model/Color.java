package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Color type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Colors", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byPalette", fields = {"paletteID"})
public final class Color implements Model {
  public static final QueryField ID = field("Color", "id");
  public static final QueryField RGB = field("Color", "rgb");
  public static final QueryField PALETTE_ID = field("Color", "paletteID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String rgb;
  private final @ModelField(targetType="ID") String paletteID;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getRgb() {
      return rgb;
  }
  
  public String getPaletteId() {
      return paletteID;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Color(String id, String rgb, String paletteID) {
    this.id = id;
    this.rgb = rgb;
    this.paletteID = paletteID;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Color color = (Color) obj;
      return ObjectsCompat.equals(getId(), color.getId()) &&
              ObjectsCompat.equals(getRgb(), color.getRgb()) &&
              ObjectsCompat.equals(getPaletteId(), color.getPaletteId()) &&
              ObjectsCompat.equals(getCreatedAt(), color.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), color.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getRgb())
      .append(getPaletteId())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Color {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("rgb=" + String.valueOf(getRgb()) + ", ")
      .append("paletteID=" + String.valueOf(getPaletteId()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Color justId(String id) {
    return new Color(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      rgb,
      paletteID);
  }
  public interface BuildStep {
    Color build();
    BuildStep id(String id);
    BuildStep rgb(String rgb);
    BuildStep paletteId(String paletteId);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String rgb;
    private String paletteID;
    @Override
     public Color build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Color(
          id,
          rgb,
          paletteID);
    }
    
    @Override
     public BuildStep rgb(String rgb) {
        this.rgb = rgb;
        return this;
    }
    
    @Override
     public BuildStep paletteId(String paletteId) {
        this.paletteID = paletteId;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String rgb, String paletteId) {
      super.id(id);
      super.rgb(rgb)
        .paletteId(paletteId);
    }
    
    @Override
     public CopyOfBuilder rgb(String rgb) {
      return (CopyOfBuilder) super.rgb(rgb);
    }
    
    @Override
     public CopyOfBuilder paletteId(String paletteId) {
      return (CopyOfBuilder) super.paletteId(paletteId);
    }
  }
  
}
