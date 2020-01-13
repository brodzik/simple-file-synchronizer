package com.brodzik.adrian.simplefilesynchronizer.handler;

import com.brodzik.adrian.simplefilesynchronizer.data.Entry;
import com.brodzik.adrian.simplefilesynchronizer.data.SyncDirection;
import com.brodzik.adrian.simplefilesynchronizer.reference.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

class EntryHandlerTest {
    @Test
    void testUpdate() {
        EntryHandler.INSTANCE.getEntries().forEach(EntryHandler.INSTANCE::remove);

        EntryHandler.INSTANCE.add(new Entry(0, "", "", "", SyncDirection.BIDIRECTIONAL, 0, true, new Date(0)));
        Entry entry = EntryHandler.INSTANCE.getEntries().get(0);
        entry.setName("test");
        EntryHandler.INSTANCE.update(entry);

        Assertions.assertEquals("test", EntryHandler.INSTANCE.getEntries().get(0).getName());
    }

    @Test
    void testAddRemove() {
        EntryHandler.INSTANCE.getEntries().forEach(EntryHandler.INSTANCE::remove);

        EntryHandler.INSTANCE.add(new Entry(0, "", "", "", SyncDirection.BIDIRECTIONAL, 0, true, new Date(0)));
        EntryHandler.INSTANCE.add(new Entry(1, "", "", "", SyncDirection.BIDIRECTIONAL, 0, true, new Date(0)));
        EntryHandler.INSTANCE.add(new Entry(2, "", "", "", SyncDirection.BIDIRECTIONAL, 0, true, new Date(0)));

        Assertions.assertEquals(3, EntryHandler.INSTANCE.getEntries().size());

        EntryHandler.INSTANCE.remove(EntryHandler.INSTANCE.getEntries().get(1));

        Assertions.assertEquals(2, EntryHandler.INSTANCE.getEntries().size());
        Assertions.assertEquals(0, EntryHandler.INSTANCE.getEntries().get(0).getId());
        Assertions.assertEquals(2, EntryHandler.INSTANCE.getEntries().get(1).getId());
    }

    @Test
    void testAddListener() {
        EntryHandler.INSTANCE.getEntries().forEach(EntryHandler.INSTANCE::remove);

        AtomicInteger triggerCount = new AtomicInteger();
        EntryHandler.INSTANCE.addListener(triggerCount::getAndIncrement);
        EntryHandler.INSTANCE.add(new Entry(0, "", "", "", SyncDirection.BIDIRECTIONAL, 0, true, new Date(0)));
        EntryHandler.INSTANCE.remove(EntryHandler.INSTANCE.getEntries().get(0));

        Assertions.assertEquals(2, triggerCount.get());
    }

    @Test
    void testSaveLoad() {
        Constants.APP_DIR.toFile().mkdirs();

        EntryHandler.INSTANCE.getEntries().forEach(EntryHandler.INSTANCE::remove);

        EntryHandler.INSTANCE.add(new Entry(0, "a", "b", "c", SyncDirection.UNIDIRECTIONAL_1, 0, true, new Date(0)));
        EntryHandler.INSTANCE.add(new Entry(1, "d", "e", "f", SyncDirection.UNIDIRECTIONAL_2, 60, true, new Date(0)));
        EntryHandler.INSTANCE.add(new Entry(2, "g", "h", "i", SyncDirection.BIDIRECTIONAL, 3600, true, new Date(0)));
        EntryHandler.INSTANCE.save();
        EntryHandler.INSTANCE.getEntries().forEach(EntryHandler.INSTANCE::remove);

        Assertions.assertEquals(0, EntryHandler.INSTANCE.getEntries().size());

        EntryHandler.INSTANCE.load();

        Entry entry = EntryHandler.INSTANCE.getEntries().get(1);

        Assertions.assertEquals(1, entry.getId());
        Assertions.assertEquals("d", entry.getName());
        Assertions.assertEquals("e", entry.getFolderA());
        Assertions.assertEquals("f", entry.getFolderB());
        Assertions.assertEquals(SyncDirection.UNIDIRECTIONAL_2, entry.getDirection());
        Assertions.assertEquals(60, entry.getFrequency());
        Assertions.assertTrue(entry.isEnabled());
        Assertions.assertEquals(new Date(0), entry.getLastSync());
    }
}
