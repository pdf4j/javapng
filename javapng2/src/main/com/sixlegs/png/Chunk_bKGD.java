/*
com.sixlegs.image.png - Java package to read and display PNG images
Copyright (C) 1998-2005 Chris Nokleberg

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Library General Public
License as published by the Free Software Foundation; either
version 2 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Library General Public License for more details.

You should have received a copy of the GNU Library General Public
License along with this library; if not, write to the
Free Software Foundation, Inc., 59 Temple Place - Suite 330,
Boston, MA  02111-1307, USA.
*/

package com.sixlegs.png;

import java.awt.Color;
import java.io.IOException;
import java.util.Map;

class Chunk_bKGD
extends PngChunk
{
    public Chunk_bKGD()
    {
        super(bKGD);
    }

    public void read(PngInputStream in, int length, Map props, PngConfig config)
    throws IOException
    {
        int colorType = PngImage.getInt(props, PngImage.COLOR_TYPE);
        int bitDepth  = PngImage.getInt(props, PngImage.BIT_DEPTH);

        Color color;
        int r, g, b;
        switch (colorType) {
        case PngImage.COLOR_TYPE_PALETTE:
            checkLength(length, 1);
            int index = in.readUnsignedByte();
            props.put(PngImage.BACKGROUND_INDEX, Integers.valueOf(index));
            int[] palette = (int[])props.get(PngImage.PALETTE);
            color = new Color(palette[index]);
            break;
            
        case PngImage.COLOR_TYPE_GRAY:
        case PngImage.COLOR_TYPE_GRAY_ALPHA:
            checkLength(length, 2);
            if (bitDepth == 16) {
                r = g = b = in.readUnsignedByte();
                int low = in.readUnsignedByte();
                props.put(PngImage.BACKGROUND_LOW_BYTES, new Color(low, low, low));
            } else {
                r = g = b = in.readUnsignedShort();
            }
            color = new Color(r, g, b);
            break;
            
        default:
            // truecolor
            checkLength(length, 6);
            if (bitDepth == 16) {
                r = in.readUnsignedByte();
                int low_r = in.readUnsignedByte();
                g = in.readUnsignedByte();
                int low_g = in.readUnsignedByte();
                b = in.readUnsignedByte();
                int low_b = in.readUnsignedByte();
                props.put(PngImage.BACKGROUND_LOW_BYTES, new Color(low_r, low_g, low_b));
            } else {
                r = in.readUnsignedShort();
                g = in.readUnsignedShort();
                b = in.readUnsignedShort();
            }
            color = new Color(r, g, b);
        }
        props.put(PngImage.BACKGROUND, color);
    }
}