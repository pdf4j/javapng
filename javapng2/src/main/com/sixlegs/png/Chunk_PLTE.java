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

import java.io.*;
import java.util.Map;

class Chunk_PLTE
extends PngChunk
{
    public Chunk_PLTE()
    {
        super(PLTE);
    }

    public void read(PngInputStream in, int length, PngImage png)
    throws IOException
    {
        if (length % 3 != 0)
            throw new PngError("PLTE chunk length indivisible by 3");
        int size = length / 3;

        int colorType = png.getColorType();
        if (colorType == PngImage.COLOR_TYPE_PALETTE) {
            if (size > (2 << png.getBitDepth()) || size > 256)
                throw new PngError("Too many palette entries");
        }

        byte[] r = new byte[size];
        byte[] g = new byte[size];
        byte[] b = new byte[size];
        for (int i = 0; i < size; i++) {
            r[i] = in.readByte();
            g[i] = in.readByte();
            b[i] = in.readByte();
        }

        switch (colorType) {
        case PngImage.COLOR_TYPE_GRAY:
        case PngImage.COLOR_TYPE_GRAY_ALPHA:
            throw new PngWarning("PLTE chunk found in grayscale image");
        }

        Map props = png.getProperties();
        props.put(PngImage.PALETTE_RED, r);
        props.put(PngImage.PALETTE_GREEN, g);
        props.put(PngImage.PALETTE_BLUE, b);
    }
}
