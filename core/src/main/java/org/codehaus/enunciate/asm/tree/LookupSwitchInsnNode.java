/***
 * ASM: a very small and fast Java bytecode manipulation framework
 * Copyright (c) 2000-2007 INRIA, France Telecom
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holders nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.codehaus.enunciate.asm.tree;

import org.codehaus.enunciate.asm.Label;
import org.codehaus.enunciate.asm.MethodVisitor;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * A node that represents a LOOKUPSWITCH instruction.
 * 
 * @author Eric Bruneton
 */
public class LookupSwitchInsnNode extends org.codehaus.enunciate.asm.tree.AbstractInsnNode {

    /**
     * Beginning of the default handler block.
     */
    public org.codehaus.enunciate.asm.tree.LabelNode dflt;

    /**
     * The values of the keys. This list is a list of {@link Integer} objects.
     */
    public List keys;

    /**
     * Beginnings of the handler blocks. This list is a list of
     * {@link org.codehaus.enunciate.asm.tree.LabelNode} objects.
     */
    public List labels;

    /**
     * Constructs a new {@link LookupSwitchInsnNode}.
     * 
     * @param dflt beginning of the default handler block.
     * @param keys the values of the keys.
     * @param labels beginnings of the handler blocks. <tt>labels[i]</tt> is
     *        the beginning of the handler block for the <tt>keys[i]</tt> key.
     */
    public LookupSwitchInsnNode(
        final org.codehaus.enunciate.asm.tree.LabelNode dflt,
        final int[] keys,
        final org.codehaus.enunciate.asm.tree.LabelNode[] labels)
    {
        super(org.codehaus.enunciate.asm.Opcodes.LOOKUPSWITCH);
        this.dflt = dflt;
        this.keys = new ArrayList(keys == null ? 0 : keys.length);
        this.labels = new ArrayList(labels == null ? 0 : labels.length);
        if (keys != null) {
            for (int i = 0; i < keys.length; ++i) {
                this.keys.add(new Integer(keys[i]));
            }
        }
        if (labels != null) {
            this.labels.addAll(Arrays.asList(labels));
        }
    }

    public int getType() {
        return LOOKUPSWITCH_INSN;
    }

    public void accept(final MethodVisitor mv) {
        int[] keys = new int[this.keys.size()];
        for (int i = 0; i < keys.length; ++i) {
            keys[i] = ((Integer) this.keys.get(i)).intValue();
        }
        Label[] labels = new Label[this.labels.size()];
        for (int i = 0; i < labels.length; ++i) {
            labels[i] = ((org.codehaus.enunciate.asm.tree.LabelNode) this.labels.get(i)).getLabel();
        }
        mv.visitLookupSwitchInsn(dflt.getLabel(), keys, labels);
    }

    public org.codehaus.enunciate.asm.tree.AbstractInsnNode clone(final Map labels) {
        LookupSwitchInsnNode clone = new LookupSwitchInsnNode(AbstractInsnNode.clone(dflt,
                labels), null, AbstractInsnNode.clone(this.labels, labels));
        clone.keys.addAll(keys);
        return clone;
    }
}
