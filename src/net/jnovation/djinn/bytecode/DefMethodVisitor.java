/*
 * Created on Feb 8, 2006
 * By Fabien Benoit - http://www.jnovation.net
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package net.jnovation.djinn.bytecode;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class DefMethodVisitor implements MethodVisitor {
    
    private TypeSet typeSet;
    
    public DefMethodVisitor(TypeSet typeSet) {
        this.typeSet = typeSet;
    }
    
    public AnnotationVisitor visitParameterAnnotation(
            int parameter,
            String desc,
            boolean visible)
    {
        typeSet.addDesc(desc);
        return null;
    }
    
    public void visitTypeInsn(int opcode, String desc) {
        if (desc.charAt(0) == '[') {
            typeSet.addDesc(desc);
        }
        else {
            typeSet.addName(desc);
        }
    }
    
    public void visitFieldInsn(
            int opcode,
            String owner,
            String name,
            String desc)
    {
        typeSet.addName(owner);
        typeSet.addDesc(desc);
    }
    
    public void visitMethodInsn(
            int opcode,
            String owner,
            String name,
            String desc)
    {
        typeSet.addName(owner);
        typeSet.addMethodDesc(desc);
    }
    
    public void visitLdcInsn(Object cst) {
        if (cst instanceof Type)
            typeSet.addType((Type) cst);
    }
    
    public void visitMultiANewArrayInsn(String desc, int dims) {
        typeSet.addDesc(desc);
    }
    
    public void visitLocalVariable(
            String name,
            String desc,
            String signature,
            Label start,
            Label end,
            int index)
    {
        //addTypeSignature(signature);
    }
    
    public AnnotationVisitor visitAnnotationDefault() {
        return null;
    }
    
    public void visitCode() {
        // Blah.
    }
    
    public void visitInsn(int opcode) {
        // Blah.
    }
    
    public void visitIntInsn(int opcode, int operand) {
        // Blah.
    }
    
    public void visitVarInsn(int opcode, int var) {
        // Blah.
    }
    
    public void visitJumpInsn(int opcode, Label label) {
        // Blah.
    }
    
    public void visitLabel(Label label) {
        // Blah.
    }
    
    public void visitIincInsn(int var, int increment) {
        // Blah.
    }
    
    public void visitTableSwitchInsn(
            int min,
            int max,
            Label dflt,
            Label[] labels)
    {
        // Blah.
    }
    
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        // Blah.
    }
    
    public void visitTryCatchBlock(
            Label start,
            Label end,
            Label handler,
            String type)
    {
        typeSet.addName(type);
    }
    
    public void visitLineNumber(int line, Label start) {
        // Blah.
    }
    
    public void visitMaxs(int maxStack, int maxLocals) {
        // Blah.
    }

    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return null;
    }

    public void visitAttribute(Attribute attr) {
        // Blah.
    }

    public void visitEnd() {
        // Blah.
    }

	public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
		// Blah.
	}
    
}