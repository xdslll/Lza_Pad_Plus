// ICacheData.aidl
package com.lza.pad.service;

// Declare any non-default types here with import statements
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.db.model.Ebook;
import java.util.List;

interface ICacheData {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<Ebook> requestEbook(in NavigationInfo nav);
}
