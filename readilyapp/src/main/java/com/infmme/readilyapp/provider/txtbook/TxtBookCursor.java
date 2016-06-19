package com.infmme.readilyapp.provider.txtbook;

import android.database.Cursor;
import com.infmme.readilyapp.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code txt_book} table.
 */
public class TxtBookCursor extends AbstractCursor implements TxtBookModel {
  public TxtBookCursor(Cursor cursor) {
    super(cursor);
  }

  /**
   * Primary key.
   */
  public long getId() {
    Long res = getLongOrNull(TxtBookColumns._ID);
    if (res == null)
      throw new NullPointerException(
          "The value of '_id' in the database was null, which is not allowed " +
              "according to the model definition");
    return res;
  }

  /**
   * Byte position of block in a file, either FB2Part or simple chunk read
   * continuously.
   */
  public int getBytePosition() {
    Integer res = getIntegerOrNull(TxtBookColumns.BYTE_POSITION);
    if (res == null)
      throw new NullPointerException(
          "The value of 'byte_position' in the database was null, which is " +
              "not allowed according to the model definition");
    return res;
  }
}
