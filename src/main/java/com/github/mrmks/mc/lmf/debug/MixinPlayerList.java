package com.github.mrmks.mc.lmf.debug;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerList;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Mixin(PlayerList.class)
public class MixinPlayerList {
    private final List<EntityPlayerMP> playerEntityList = new List<EntityPlayerMP>() {

        final List<EntityPlayerMP> delegate = Lists.<EntityPlayerMP>newArrayList();

        @Override
        public int size() {
            return delegate.size();
        }

        @Override
        public boolean isEmpty() {
            return delegate.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return delegate.contains(o);
        }

        @NotNull
        @Override
        public Iterator<EntityPlayerMP> iterator() {
            return delegate.iterator();
        }

        @NotNull
        @Override
        public Object[] toArray() {
            return delegate.toArray();
        }

        @NotNull
        @Override
        public <T> T[] toArray(@NotNull T[] a) {
            return delegate.toArray(a);
        }

        @Override
        public boolean add(EntityPlayerMP playerMP) {
            return delegate.add(playerMP);
        }

        @Override
        public boolean remove(Object o) {
            Thread.dumpStack();
            return delegate.remove(o);
        }

        @Override
        public boolean containsAll(@NotNull Collection<?> c) {
            return delegate.containsAll(c);
        }

        @Override
        public boolean addAll(@NotNull Collection<? extends EntityPlayerMP> c) {
            return delegate.addAll(c);
        }

        @Override
        public boolean addAll(int index, @NotNull Collection<? extends EntityPlayerMP> c) {
            return delegate.addAll(index, c);
        }

        @Override
        public boolean removeAll(@NotNull Collection<?> c) {
            return delegate.removeAll(c);
        }

        @Override
        public boolean retainAll(@NotNull Collection<?> c) {
            return delegate.retainAll(c);
        }

        @Override
        public void clear() {
            delegate.clear();
        }

        @Override
        public EntityPlayerMP get(int index) {
            return delegate.get(index);
        }

        @Override
        public EntityPlayerMP set(int index, EntityPlayerMP element) {
            return delegate.set(index, element);
        }

        @Override
        public void add(int index, EntityPlayerMP element) {
            delegate.add(index, element);
        }

        @Override
        public EntityPlayerMP remove(int index) {
            return delegate.remove(index);
        }

        @Override
        public int indexOf(Object o) {
            return delegate.indexOf(o);
        }

        @Override
        public int lastIndexOf(Object o) {
            return delegate.lastIndexOf(o);
        }

        @NotNull
        @Override
        public ListIterator<EntityPlayerMP> listIterator() {
            return delegate.listIterator();
        }

        @NotNull
        @Override
        public ListIterator<EntityPlayerMP> listIterator(int index) {
            return delegate.listIterator(index);
        }

        @NotNull
        @Override
        public List<EntityPlayerMP> subList(int fromIndex, int toIndex) {
            return delegate.subList(fromIndex, toIndex);
        }
    };
}
