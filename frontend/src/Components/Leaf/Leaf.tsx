import React from 'react';
import { Text } from 'slate';
import { ReactEditor } from 'slate-react';

interface LeafProps {
  attributes: React.HTMLProps<HTMLElement>;
  children: React.ReactNode;
  leaf: Text;
}

export function Leaf({ attributes, children, leaf }: LeafProps) {
  let style: React.CSSProperties = {};

  if (leaf.fontSize) {
    style.fontSize = leaf.fontSize;
  }

  if (leaf.bold) {
    style.fontWeight = 'bold';
  }

  return (
    <span {...attributes} style={style}>
      {children}
    </span>
  );
}
