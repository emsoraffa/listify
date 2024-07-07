export type ListDto = {
  list_name: string,
  author: string,
  listitems: string[],

}

export type CheckListItemDto = {
  text: string,
  checked: boolean,
}
