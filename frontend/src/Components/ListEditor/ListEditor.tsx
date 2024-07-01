
import React, { useMemo, useState, useCallback } from 'react';
import { createEditor, Descendant } from 'slate';
import { Slate, Editable, withReact } from 'slate-react';
import { withHistory } from 'slate-history';


export function ListEditor() {
  const [editor] = useState(() => withReact(createEditor()))
  const [value, setValue] = useState<Descendant[]>();

  const initialValue: Descendant[] = [
    {
      type: 'paragraph',
      children: [{ text: 'A line of text in a paragraph.' }],
    },
  ]

  return (
    <Slate editor={editor} initialValue={initialValue}>
      <Editable
        onKeyDown={event => {
          if (event.key === '&') {
            // Prevent the ampersand character from being inserted.
            event.preventDefault()
            // Execute the `insertText` method when the event occurs.
            editor.insertText('and')
          }
        }}
      />
    </Slate>
  );
};


