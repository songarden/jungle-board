import Item from "./Item"
import Comment from "../../components/Comment";
import './Items.css';

const ItemList = ({ itemList, onEdit, onDelete, onEditComplete, setBoardData, boardData }) => {
    let isEmpty = false;
    if (!itemList) {
        isEmpty = true;
    }     
    
    return (
        !isEmpty && 
        <div>
            {itemList.map((e) => (
                <div className="item-and-comment" key={e.boardId}>
                    <Item
                        {...e} 
                        onEdit={onEdit}
                        onDelete={onDelete}  
                        onEditComplete={onEditComplete}  
                        setBoardData={setBoardData} 
                        boardData={boardData}
                    />
                    <Comment boardId={e.boardId} />
                </div>
            ))}
        </div>
    )
}


export default ItemList;