
import React, { useMemo, useImperativeHandle, forwardRef } from 'react';
import { Slate, Editable, withReact, ReactEditor } from 'slate-react';
import { createEditor, Descendant } from 'slate';

interface TitleEditorProps {
  title: Descendant[];
  setTitle: (value: Descendant[]) => void;
  onFocusNext: () => void;
}

interface TitleEditorRef {
  focus: () => void;
}

export const TitleEditor = forwardRef<TitleEditorRef, TitleEditorProps>(
  ({ title, setTitle, onFocusNext }, ref) => {
    const editor = useMemo(() => withReact(createEditor()), []);

    useImperativeHandle(ref, () => ({
      focus: () => {
        ReactEditor.focus(editor);
      }
    }));

    const handleKeyDown = (event: React.KeyboardEvent) => {
      if (event.key === 'Enter') {
        event.preventDefault();
        onFocusNext();
      }
    };

    return (
      <Slate
        editor={editor}
        initialValue={title}
        onChange={newValue => setTitle(newValue)}
      >
        <Editable
          onKeyDown={handleKeyDown}
          placeholder="Enter list title here"
          style={{ fontWeight: 'bold', fontSize: '24px', margin: '10px 0' }}
        />
      </Slate>
    );
  }
);

